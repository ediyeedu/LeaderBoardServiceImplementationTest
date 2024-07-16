package euro_24.prize.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MatchBoardResponse {

    private Long userId;
    private Long matchId;
    private String firstName;
    private String lastname;
    private String email;
    private double score;
    private int rank;

}
