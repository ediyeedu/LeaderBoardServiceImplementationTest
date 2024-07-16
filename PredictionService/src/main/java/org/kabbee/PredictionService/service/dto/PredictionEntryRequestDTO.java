package org.kabbee.PredictionService.service.dto;

public record PredictionEntryRequestDTO(
        Long questionId,
        String value
) {
}
