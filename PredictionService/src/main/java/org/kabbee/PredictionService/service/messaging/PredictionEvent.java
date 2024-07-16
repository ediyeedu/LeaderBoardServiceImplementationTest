package org.kabbee.PredictionService.service.messaging;



import java.time.LocalDateTime;



public record PredictionEvent(
        Long predictionId,
        Long matchId,
        Long userId,
        LocalDateTime predictionTime,
        double totalScore
) {
}
