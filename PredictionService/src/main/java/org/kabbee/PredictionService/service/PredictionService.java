package org.kabbee.PredictionService.service;


import org.kabbee.PredictionService.model.Answer;
import org.kabbee.PredictionService.service.dto.PredictionRequestDTO;
import org.kabbee.PredictionService.service.dto.PredictionResponseDTO;

import java.util.List;

public interface PredictionService {
    List<PredictionResponseDTO> getPredictions();
    PredictionResponseDTO getPrediction(Long id);
    PredictionResponseDTO createPrediction(PredictionRequestDTO prediction);
    PredictionResponseDTO updatePrediction(Long id, PredictionRequestDTO prediction);
    void deletePrediction(Long id);
    void evaluatePredictions(Answer answer);
}
