package org.kabbee.PredictionService.service.dto;

import java.util.List;

public record QuizDto(
        Long id,
        Long matchId,
        List<QuestionDto> questions
) {
}
