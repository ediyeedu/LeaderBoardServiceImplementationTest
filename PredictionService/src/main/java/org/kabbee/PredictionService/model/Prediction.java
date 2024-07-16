package org.kabbee.PredictionService.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Prediction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long matchId;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.EAGER)
    private List<PredictionEntry> predictionEntries;
    private LocalDateTime predictionTime;
    private double totalScore;

    public void calculateTotalScore() {
        int bonus = 100;
        double sum = 0;
        for (PredictionEntry entry : predictionEntries) {
            sum += entry.getScore();
        }
        if(sum == 100) {
            sum += bonus;
        }
        setTotalScore(sum);
    }

}
