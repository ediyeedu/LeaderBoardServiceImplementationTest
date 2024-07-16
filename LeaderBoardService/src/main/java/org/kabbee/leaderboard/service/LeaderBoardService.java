package org.kabbee.leaderboard.service;



import org.kabbee.leaderboard.dto.PredictionEvent;
import org.kabbee.leaderboard.dto.response.MatchBoardResponse;
import org.kabbee.leaderboard.dto.response.TotalBoardResponse;
import org.kabbee.leaderboard.model.LeaderBoard;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface LeaderBoardService {


    void processPredictions(List<PredictionEvent> predictions);
    List<MatchBoardResponse> calculateAndSendLeaderboardUpdates(Long matchId);
    List<TotalBoardResponse> calculateAndSendTotalRankUpdates();

    List<LeaderBoard> getAllLeaderBoards();


}











