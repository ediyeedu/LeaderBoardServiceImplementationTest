package euro_24.prize.dto.response;

import euro_24.prize.enums.PrizeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrizeResponse {

    private String firstName;
    private String lastName;
    private double score;
    private int rank;
    private String prizeType; // "MATCH" or "TOTAL"
    private String prizeDescription;

    @Enumerated(EnumType.STRING)
    private PrizeStatus status;
    private LocalDateTime confirmedAt;
}
