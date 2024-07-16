package org.kabbee.PredictionService.service.dto;

public record PredictionEntryResponseDTO(
        Long id,
        Long questionId,
        String value,
        double score
) {

}
