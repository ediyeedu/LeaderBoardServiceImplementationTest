package org.kabbee.PredictionService.service.dto;


import java.time.LocalDateTime;
import java.util.List;

public record PredictionResponseDTO(
        Long id,
        Long userId,
        Long matchId,
        List<PredictionEntryResponseDTO> predictionEntries,
        LocalDateTime predictionTime,
        double totalScore
) {
}
