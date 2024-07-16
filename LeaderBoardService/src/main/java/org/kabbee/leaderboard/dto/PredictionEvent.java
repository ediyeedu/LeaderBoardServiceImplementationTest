package org.kabbee.leaderboard.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictionEvent {

    @NotNull(message = "predictionId can not be null")
    private Long predictionId;
    @Min(value = 1)
    @NotNull(message = "matchId can not be null")
    private Long matchId;
   // private Long QuizId;
    @Min(value = 1)
    @NotNull(message = "userId can not be null")
    private Long userId;
    //private QuizScope quizScope;
    @NotNull(message = "Local date and time can not be null")
    private LocalDateTime predictionTime;
    @NotNull(message = "total score can not be null")
    @Min(value = 0)
    private double totalScore;
}
