package org.kabbee.leaderboard.controller;


import lombok.RequiredArgsConstructor;
import org.kabbee.leaderboard.dto.PredictionEvent;
import org.kabbee.leaderboard.dto.UserDto;
import org.kabbee.leaderboard.dto.response.MatchBoardResponse;
import org.kabbee.leaderboard.dto.response.TotalBoardResponse;
import org.kabbee.leaderboard.events.listener.PredictionListener;
import org.kabbee.leaderboard.model.LeaderBoard;
import org.kabbee.leaderboard.model.User;
import org.kabbee.leaderboard.service.LeaderBoardService;
import org.kabbee.leaderboard.service.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class TestController {

    private final LeaderBoardService leaderBoardService;

    private static final Logger logger = LoggerFactory.getLogger( PredictionListener.class);
    private final UserServiceClient user;

    private final UserServiceClient useClient;

    @PostMapping("/processPredictions")
    public ResponseEntity<Void> processPredictions(@RequestBody List<PredictionEvent> predictions) {
        logger.info("Received API call to process predictions: {}", predictions);
        leaderBoardService.processPredictions(predictions);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/leaderboardUpdates/{matchId}")
    public ResponseEntity<List<MatchBoardResponse>> calculateAndSendLeaderboardUpdates(@PathVariable Long matchId) {
        List<MatchBoardResponse> response = leaderBoardService.calculateAndSendLeaderboardUpdates(matchId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/totalRankUpdates")
    public ResponseEntity<List<TotalBoardResponse>> calculateAndSendTotalRankUpdates() {
        List<TotalBoardResponse> response = leaderBoardService.calculateAndSendTotalRankUpdates();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LeaderBoard>> getAllLeaderBoards() {
        List<LeaderBoard> response = leaderBoardService.getAllLeaderBoards();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<?> getLeaderBoard(@PathVariable Long id) {
        try {
           User user1 = user.getUserById(id);
            return ResponseEntity.ok(user1);
        } catch (Exception e) {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {

        return useClient.getUserById(id);
    }

}
