package org.kabbee.PredictionService.service;

import org.kabbee.PredictionService.service.dto.QuizDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(name = "questionService", url = "${quiz.service.url}")
public interface QuestionFeignClient {
    @RequestMapping("/questions/quiz-match-id/{matchId}")
    QuizDto getQuizByMatchId(@PathVariable("matchId") Long matchId);
}
