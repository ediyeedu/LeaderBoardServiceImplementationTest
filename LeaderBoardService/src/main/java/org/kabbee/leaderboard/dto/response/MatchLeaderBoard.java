package org.kabbee.leaderboard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MatchLeaderBoard {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("score")
    private double score;

    @JsonProperty("rank")
    private int rank;
}
