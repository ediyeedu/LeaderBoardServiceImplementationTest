package org.kabbee.leaderboard.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kabbee.leaderboard.dto.response.MatchBoardResponse;
import org.kabbee.leaderboard.dto.response.TotalBoardResponse;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrizeEvent {
    private List<MatchBoardResponse> matchBoardResponses;
    private List<TotalBoardResponse> totalBoardResponses;
}
