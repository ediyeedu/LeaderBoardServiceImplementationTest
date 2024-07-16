package org.kabbee.PredictionService.service.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PredictionRequestDTO(
        Long userId,
        Long matchId,
        List<PredictionEntryRequestDTO> predictionEntries,
        LocalDateTime predictionTime
) {
}
