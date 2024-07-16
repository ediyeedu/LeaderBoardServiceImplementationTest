package org.kabbee.PredictionService.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.kabbee.PredictionService.model.Answer;
import org.kabbee.PredictionService.model.Prediction;
import org.kabbee.PredictionService.repository.PredictionRepository;
import org.kabbee.PredictionService.service.PredictionService;
import org.kabbee.PredictionService.service.ProcessingService;
import org.kabbee.PredictionService.service.adapter.PredictionAdapter;
import org.kabbee.PredictionService.service.dto.PredictionRequestDTO;
import org.kabbee.PredictionService.service.dto.PredictionResponseDTO;
import org.kabbee.PredictionService.service.messaging.PredictionEvent;
import org.kabbee.PredictionService.service.messaging.PredictionListEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableAsync
public class PredictionServiceImpl implements PredictionService {

    private final PredictionRepository predictionRepository;
    private final ProcessingService processingService;
    private final KafkaTemplate<String, PredictionListEvent> kafkaTemplate;

    @Override
    public List<PredictionResponseDTO> getPredictions() {
        return predictionRepository.findAll().stream()
                .map(PredictionAdapter::toDTO)
                .toList();
    }

    @Override
    public PredictionResponseDTO getPrediction(Long id) {
        Prediction prediction = predictionRepository.findById(id).orElse(null);
        if(prediction == null) {
            return null;
        }
        return PredictionAdapter.toDTO(prediction);
    }

    @Override
    public PredictionResponseDTO createPrediction(PredictionRequestDTO predictionDTO) {
        Prediction prediction = PredictionAdapter.toEntity(predictionDTO);
        return PredictionAdapter.toDTO(predictionRepository.save(prediction));
    }

    @Override
    public PredictionResponseDTO updatePrediction(Long id, PredictionRequestDTO predictionDTO) {
        Prediction prediction = predictionRepository.findById(id).orElse(null);
        if(prediction == null) {
            return null;
        }
        Prediction UpdatedPrediction = PredictionAdapter.toEntity(predictionDTO);
        UpdatedPrediction.setId(id);
        return PredictionAdapter.toDTO(predictionRepository.save(UpdatedPrediction));
    }

    @Override
    public void deletePrediction(Long id) {
        predictionRepository.deleteById(id);
    }

    @Async
    @Override
    public void evaluatePredictions(Answer answer) {
        List<Prediction> predictions = predictionRepository.findAllByMatchId(answer.getMatchId());
        if(predictions.isEmpty()) {
           return;
        }

        List<PredictionResponseDTO> predictionList = processingService.evaluate(predictions, answer);
        List<PredictionEvent> predictionEventList = convertToPredictionEvent(predictionList);
        kafkaTemplate.send("prediction", new PredictionListEvent(predictionEventList));
        System.out.println("predictionList sent***************8888888888**************88888888******" + predictionList);
    }

    private List<PredictionEvent> convertToPredictionEvent(List<PredictionResponseDTO> predictionResponseDTOList) {
        return predictionResponseDTOList.stream()
                .map(PredictionAdapter::toEvent)
                .toList();
    }

}
