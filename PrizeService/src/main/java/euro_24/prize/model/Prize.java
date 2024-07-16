package euro_24.prize.model;

import euro_24.prize.enums.PrizeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


    public void setUserId(Long userId){
        this.userId = userId;
    }

    public void setMatchId(Long matchId){
        this.matchId = matchId;
    }
}
