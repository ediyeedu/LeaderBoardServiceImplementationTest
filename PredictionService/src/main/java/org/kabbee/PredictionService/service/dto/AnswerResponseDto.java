package org.kabbee.PredictionService.service.dto;


import org.kabbee.PredictionService.model.AnswerEntry;

import java.util.List;

public record AnswerResponseDto(
        Long id,
        Long matchId,
        List<AnswerEntry> answerEntries
) {
}
