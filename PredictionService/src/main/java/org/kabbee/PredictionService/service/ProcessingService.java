package org.kabbee.PredictionService.service;

import lombok.RequiredArgsConstructor;
import org.kabbee.PredictionService.model.Answer;
import org.kabbee.PredictionService.model.AnswerEntry;
import org.kabbee.PredictionService.model.Prediction;
import org.kabbee.PredictionService.model.PredictionEntry;
import org.kabbee.PredictionService.repository.PredictionRepository;
import org.kabbee.PredictionService.service.adapter.PredictionAdapter;
import org.kabbee.PredictionService.service.dto.PredictionResponseDTO;
import org.kabbee.PredictionService.service.dto.QuestionDto;
import org.kabbee.PredictionService.service.dto.QuizDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor

public class  ProcessingService {
    private final PredictionRepository predictionRepository;
    private final QuestionFeignClient questionFeignClient;
    private static final double HIGHEST_POSSIBLE_SCORE_FOR_GUESSING_ACTIVITIES = 10;
    private static final double HIGHEST_POSSIBLE_SCORE_FOR_GUESSING_TIME = 90;

    private static final Logger logger = LoggerFactory.getLogger(ProcessingService.class);
    public List<PredictionResponseDTO> evaluate(List<Prediction> predictionList, Answer answer) {
        logger.info ( "*************8888888888888888**************8888888888*****" + answer.getMatchId ());
        QuizDto quiz = questionFeignClient.getQuizByMatchId(answer.getMatchId());
        logger.info ( "*************8888888888888888**************8888888888*****" + quiz);
        List<QuestionDto> questionDtoList = quiz.questions();
        for(QuestionDto questionDto : questionDtoList) {
            if(questionDto.questionScope().name().equalsIgnoreCase("MATCH")) {
                Long questionId = questionDto.id();
                AnswerEntry answerEntry = answer.getAnswerEntryByQuestionId(questionId);
                String questionType = questionDto.type().name();
                predictionList.forEach(prediction -> {
                    prediction.getPredictionEntries()
                            .forEach(predictionEntry -> {
                                double score = calculateScore(questionId, questionType, answerEntry.getValue(), predictionEntry);
                                predictionEntry.setScore(score);
                            });
                    prediction.calculateTotalScore();
                        }
                );
                predictionRepository.saveAll(predictionList);
            }
    }
        return predictionList.stream().map(PredictionAdapter::toDTO).toList();
    }


    private double calculateScore(Long questionId, String questionType, String actualAnswer, PredictionEntry predictionEntry) {
        double factor;
        String predictedAnswer = predictionEntry.getValue();
        if(questionType.equalsIgnoreCase("Count") && questionId.equals(predictionEntry.getQuestionId())) {
            int actualAnswerInt = Integer.parseInt(actualAnswer);
            factor = HIGHEST_POSSIBLE_SCORE_FOR_GUESSING_ACTIVITIES / actualAnswerInt;
            return HIGHEST_POSSIBLE_SCORE_FOR_GUESSING_ACTIVITIES - (Math.abs(actualAnswerInt - Integer.parseInt(predictedAnswer)) * factor);
        }
        if(questionType.equalsIgnoreCase("Time") && questionId.equals(predictionEntry.getQuestionId()) ) {
            int actualAnswerInt = Integer.parseInt(actualAnswer);
            factor = HIGHEST_POSSIBLE_SCORE_FOR_GUESSING_TIME / actualAnswerInt;
            return HIGHEST_POSSIBLE_SCORE_FOR_GUESSING_TIME - (factor * Math.abs(actualAnswerInt - Integer.parseInt(predictedAnswer)));
        }
        else {
            return actualAnswer.equalsIgnoreCase(predictedAnswer) &&
                    questionId.equals(predictionEntry.getQuestionId()) ? HIGHEST_POSSIBLE_SCORE_FOR_GUESSING_ACTIVITIES : 0;
        }
    }
}

