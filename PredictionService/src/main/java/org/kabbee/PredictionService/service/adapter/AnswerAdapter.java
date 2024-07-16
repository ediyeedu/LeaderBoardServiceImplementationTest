package org.kabbee.PredictionService.service.adapter;


import org.kabbee.PredictionService.model.Answer;
import org.kabbee.PredictionService.service.dto.AnswerRequestDto;
import org.kabbee.PredictionService.service.dto.AnswerResponseDto;

public class AnswerAdapter {

    public static AnswerResponseDto toDto(Answer answer) {
        return new AnswerResponseDto(answer.getId(),
                answer.getMatchId(),
                answer.getAnswerEntries());
    }

    public static Answer toEntity(AnswerRequestDto answerRequestDto) {
        return new Answer(
                null,
                answerRequestDto.matchId(),
                answerRequestDto.answerEntries());
    }
}
