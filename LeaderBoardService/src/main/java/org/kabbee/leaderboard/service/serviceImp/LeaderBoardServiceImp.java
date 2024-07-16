package org.kabbee.leaderboard.service.serviceImp;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kabbee.leaderboard.dto.PredictionEvent;
import org.kabbee.leaderboard.dto.PrizeEvent;
import org.kabbee.leaderboard.dto.response.MatchBoardResponse;
import org.kabbee.leaderboard.dto.response.MatchLeaderBoard;
import org.kabbee.leaderboard.dto.response.TotalBoardResponse;
import org.kabbee.leaderboard.dto.response.TournamentLeaderBoard;
import org.kabbee.leaderboard.events.sender.LeaderBoardEvent;
import org.kabbee.leaderboard.model.LeaderBoard;
import org.kabbee.leaderboard.model.MatchEntry;
import org.kabbee.leaderboard.model.User;
import org.kabbee.leaderboard.repository.LeaderBoardRepository;
import org.kabbee.leaderboard.repository.MatchEntryRepository;
import org.kabbee.leaderboard.service.LeaderBoardService;
import org.kabbee.leaderboard.service.UserServiceClient;
import org.kabbee.leaderboard.service.WebSocketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@Transactional
@RequiredArgsConstructor
public class LeaderBoardServiceImp implements LeaderBoardService {

    private final LeaderBoardRepository leaderBoardRepository;
    private final MatchEntryRepository matchEntryRepository;
    private final WebSocketService webSocketService;
    private final UserServiceClient userServiceClient;
    private final LeaderBoardEvent leaderBoardEvent;

    @Override
    public void processPredictions(List<PredictionEvent> predictions) {
        try {
            Map<Long, LeaderBoard> leaderBoardMap = new HashMap<> ();

            for (PredictionEvent prediction : predictions) {
                LeaderBoard leaderBoard = leaderBoardMap.computeIfAbsent ( prediction.getUserId (), userId ->
                        leaderBoardRepository.findByUserId ( userId ).orElseGet ( () -> createNewLeaderBoard ( userId, prediction ) )
                );

                MatchEntry matchEntry = createMatchEntry ( prediction );
                leaderBoard.getMatchEntries ().add ( matchEntry );
                leaderBoard.setTotalScore ( leaderBoard.getMatchEntries ().stream ().mapToDouble ( MatchEntry::getScore ).sum () );
                leaderBoardRepository.save ( leaderBoard );
            }

            if (!predictions.isEmpty ()) {
                Long matchId = predictions.get ( 0 ).getMatchId ();
                List<MatchBoardResponse> matchBoardResponses = calculateAndSendLeaderboardUpdates (matchId);
                List<TotalBoardResponse> totalBoardResponses = calculateAndSendTotalRankUpdates ();

                PrizeEvent prizeEvent = PrizeEvent.builder ()
                        .matchBoardResponses ( matchBoardResponses )
                        .totalBoardResponses ( totalBoardResponses )
                        .build ();
                leaderBoardEvent.sendTopUsers ( prizeEvent );
                System.out.println ("****&&&&&*******######( prizeEvent((&&%*&(%$$$$$&*&^(*T&E%E&&T*Y(Y******" + prizeEvent);
            }
        }catch (Exception e) {
            log.error("Error processing predictions", e);
            throw new RuntimeException("Error processing predictions", e);
        }
    }

    private LeaderBoard createNewLeaderBoard(Long userId, PredictionEvent prediction) {
        List<MatchEntry> matchEntries = new ArrayList<>();
        matchEntries.add(createMatchEntry(prediction));

        return LeaderBoard.builder()
                .userId(userId)
                .totalScore(prediction.getTotalScore())
                .matchEntries(matchEntries)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    private MatchEntry createMatchEntry(PredictionEvent prediction) {
        return MatchEntry.builder()
                .matchId(prediction.getMatchId())
                .score(prediction.getTotalScore())
                .predictionTime(prediction.getPredictionTime())
                .build();
    }

    @Override
    public List<MatchBoardResponse> calculateAndSendLeaderboardUpdates(Long matchId) {
        List<MatchEntry> matchEntries = matchEntryRepository.findByMatchId(matchId);

        Map<Long, User> userCache = new HashMap<>();
        Map<Long, LeaderBoard> leaderBoardCache = new HashMap<>();

        for (MatchEntry entry : matchEntries) {
            LeaderBoard leaderBoard = leaderBoardCache.computeIfAbsent(entry.getId(), id ->
                    leaderBoardRepository.findByMatchEntries(entry.getId()).orElse(null)
            );

            if (leaderBoard != null) {
                Long userId = leaderBoard.getUserId();
                userCache.computeIfAbsent(userId, userServiceClient::getUserById);
            }
        }

        List<MatchBoardResponse> rankedResponses = getRankedResponses(matchEntries, leaderBoardCache, userCache);

        for (MatchBoardResponse response : rankedResponses) {
            MatchLeaderBoard matchLeader = MatchLeaderBoard.builder ()
                    .firstName (response.getFirstName ())
                    .lastname ( response.getLastname ())
                    .score ( response.getScore ())
                    .rank ( response.getRank ()).build ();
            webSocketService.sendLeaderBoardUpdate(matchLeader);
            System.out.println ("&&&&**********###### Match &&&&&&&&^^^^^$$$$$$$$%%%%%^*$$$$%%^(*^&(&^*$" + matchLeader);
        }

        return rankedResponses;
    }

    private List<MatchBoardResponse> getRankedResponses(List<MatchEntry> matchEntries, Map<Long, LeaderBoard> leaderBoardCache, Map<Long, User> userCache) {
        List<MatchEntry> sortedEntries = matchEntries.stream()
                .sorted(Comparator.comparingDouble(MatchEntry::getScore).reversed())
                .collect(Collectors.toList());

        AtomicInteger rankCounter = new AtomicInteger(1);

        return sortedEntries.stream().map(entry -> {
            LeaderBoard leaderBoard = leaderBoardCache.get(entry.getId());
            if (leaderBoard != null) {
                Long userId = leaderBoard.getUserId();
                User user = userCache.get(userId);
                return MatchBoardResponse.builder()
                        .userId (userId)
                        .matchId (entry.getMatchId ())
                        .firstName(user.getFirstname())
                        .lastname(user.getLastName())
                        .email (user.getEmail ())
                        .score(entry.getScore())
                        .rank(rankCounter.getAndIncrement())
                        .build();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<TotalBoardResponse> calculateAndSendTotalRankUpdates() {
        List<LeaderBoard> leaderBoards = leaderBoardRepository.findAll();

        Map<Long, User> userCache = new HashMap<>();

        for (LeaderBoard leaderBoard : leaderBoards) {
            Long userId = leaderBoard.getUserId();
            userCache.computeIfAbsent(userId, userServiceClient::getUserById);
        }

        List<TotalBoardResponse> totalRankedResponses = getTotalRankedResponses(leaderBoards, userCache);

        for (TotalBoardResponse response : totalRankedResponses) {
            TournamentLeaderBoard tournamentBoard = TournamentLeaderBoard.builder ()
                    .firstName (response.getFirstName ())
                    .lastname ( response.getLastname ())
                    .totalScore ( response.getTotalScore ())
                    .rank ( response.getRank ()).build ();

            webSocketService.sendTotalLeaderBoardUpdate(tournamentBoard);
            System.out.println ("&&&&&&tounament&&&&&&@@@@@*****######&&&&((((@^$^$@^&$T$*R$^#R$#&@$RUY$R*$#YR*$" + tournamentBoard);
        }

        return totalRankedResponses;
    }

    @Override
    public List<LeaderBoard> getAllLeaderBoards() {

        return leaderBoardRepository.findAll();
    }

    private List<TotalBoardResponse> getTotalRankedResponses(List<LeaderBoard> leaderBoards, Map<Long, User> userCache) {
        List<LeaderBoard> sortedLeaderBoards = leaderBoards.stream()
                .sorted(Comparator.comparingDouble(LeaderBoard::getTotalScore).reversed())
                .collect(Collectors.toList());

        AtomicInteger rankCounter = new AtomicInteger(1);

        return sortedLeaderBoards.stream().map(leaderBoard -> {
            Long userId = leaderBoard.getUserId();
            User user = userCache.get(userId);
            return TotalBoardResponse.builder()
                    .userId (userId)
                    .firstName(user.getFirstname())
                    .lastname(user.getLastName())
                    .email (user.getEmail ())
                    .totalScore(leaderBoard.getTotalScore())
                    .rank(rankCounter.getAndIncrement())
                    .build();
        }).collect(Collectors.toList());
    }
}