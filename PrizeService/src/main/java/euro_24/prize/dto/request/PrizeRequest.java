package euro_24.prize.dto.request;

import euro_24.prize.enums.PrizeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrizeRequest {

    @NotNull(message = "userId cannot be null")
    private Long userId;

    @NotNull(message = "MatchId cannot be null")
    private Long matchId;

    @NotEmpty(message = "firstName cannot be null")
    private String firstName;

    @NotEmpty(message = "lastName cannot be null")
    private String lastName;

    @NotNull(message = "score cannot be null")
    private double score;

    @NotNull(message = "rank cannot be null")
    private int rank;

    @NotEmpty(message = "prizeType cannot be null")
    private String prizeType; // "MATCH" or "TOTAL"

    @NotEmpty(message = "prizeDescription cannot be null")
    private String prizeDescription;

    @NotEmpty(message = "prizeStatus cannot be null")
    @Enumerated(EnumType.STRING)
    private PrizeStatus prizeStatus;


}
