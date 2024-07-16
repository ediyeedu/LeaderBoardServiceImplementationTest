package org.kabbee.leaderboard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalBoardResponse {

    private Long userId;
    private Long matchId;
    private String firstName;
    private String lastname;
    private String email;
    private double totalScore;
    private int rank;

}
