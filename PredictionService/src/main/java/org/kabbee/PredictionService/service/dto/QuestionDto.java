package org.kabbee.PredictionService.service.dto;

public record QuestionDto(
    Long id,
    String question,
    QuestionType type,
    QuestionScope questionScope
) {
}
