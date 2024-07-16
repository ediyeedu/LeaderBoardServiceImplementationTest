package euro_24.prize.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data

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
