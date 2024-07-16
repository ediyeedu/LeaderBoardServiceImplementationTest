package org.kabbee.PredictionService.service;


import org.kabbee.PredictionService.service.dto.AnswerRequestDto;
import org.kabbee.PredictionService.service.dto.AnswerResponseDto;

import java.util.List;

public interface AnswerService {

    List<AnswerResponseDto> getAllAnswers();
    AnswerResponseDto saveAnswer(AnswerRequestDto answerRequestDto);
    AnswerResponseDto updateAnswer(Long id, AnswerRequestDto answerRequestDto);
    void deleteAnswer(Long id);
    AnswerResponseDto getAnswerById(Long id);
    AnswerResponseDto getAnswerByMatchId(Long matchId);
}
