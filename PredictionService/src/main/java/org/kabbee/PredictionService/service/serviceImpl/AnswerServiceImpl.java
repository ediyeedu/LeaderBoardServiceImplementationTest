package org.kabbee.PredictionService.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.kabbee.PredictionService.model.Answer;
import org.kabbee.PredictionService.repository.AnswerRepository;
import org.kabbee.PredictionService.service.AnswerService;
import org.kabbee.PredictionService.service.PredictionService;
import org.kabbee.PredictionService.service.adapter.AnswerAdapter;
import org.kabbee.PredictionService.service.dto.AnswerRequestDto;
import org.kabbee.PredictionService.service.dto.AnswerResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final PredictionService predictionService;
    @Override
    public List<AnswerResponseDto> getAllAnswers() {
        return answerRepository.findAll().stream()
                .map(AnswerAdapter::toDto)
                .toList();
    }

    @Override
    public AnswerResponseDto saveAnswer(AnswerRequestDto answerRequestDto) {
        Answer answer = AnswerAdapter.toEntity(answerRequestDto);
        Answer existingAnswer = answerRepository.findByMatchId(answer.getMatchId())
                .orElse(null);
        if(existingAnswer != null) {
            throw new RuntimeException("The answer for this game has already been submitted");
        }
        answer = answerRepository.save(answer);
        System.out.println (answer + " Answer Test 1" + " **********************************************************");
        predictionService.evaluatePredictions(answer);
        return AnswerAdapter.toDto(answer);
    }

    @Override
    public AnswerResponseDto updateAnswer(Long id, AnswerRequestDto answerRequestDto) {
        Answer answer = answerRepository.findById(id).orElse(null);
        if(answer == null) {
            return null;
        }
        Answer updatedAnswer = AnswerAdapter.toEntity(answerRequestDto);
        updatedAnswer.setId(id);
        return AnswerAdapter.toDto(answerRepository.save(updatedAnswer));
    }

    @Override
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public AnswerResponseDto getAnswerById(Long id) {
        return answerRepository.findById(id)
                .map(AnswerAdapter::toDto)
                .orElse(null);
    }

    @Override
    public AnswerResponseDto getAnswerByMatchId(Long matchId) {
        return answerRepository.findByMatchId(matchId)
                .map(AnswerAdapter::toDto)
                .orElse(null);
    }
}
