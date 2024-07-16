package euro_24.email.dto;


import euro_24.email.enums.PrizeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PrizeDto {

    private Long userId;
    private Long matchId;
    private String firstName;
    private String lastName;
    private String email;
    private double score;
    private int rank;
    private String prizeType; // "MATCH" or "TOTAL"
    private String prizeDescription; // Prize description
    private LocalDateTime confirmedAt;

    @Enumerated(EnumType.STRING)
    private PrizeStatus prizeStatus;
}
