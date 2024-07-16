package org.kabbee.PredictionService.service.adapter;


import org.kabbee.PredictionService.model.Prediction;
import org.kabbee.PredictionService.service.dto.PredictionRequestDTO;
import org.kabbee.PredictionService.service.dto.PredictionResponseDTO;
import org.kabbee.PredictionService.service.messaging.PredictionEvent;

public class PredictionAdapter {

    public static PredictionResponseDTO toDTO(Prediction prediction) {
        return new PredictionResponseDTO(
                prediction.getId(),
                prediction.getUserId(),
                prediction.getMatchId(),
                prediction.getPredictionEntries().stream()
                        .map(PredictionEntryAdapter::toDTO)
                        .toList(),
                prediction.getPredictionTime(),
                prediction.getTotalScore()
        );
    }

    public static Prediction toEntity(PredictionRequestDTO predictionRequestDTO) {
        return new Prediction(
                null,
                predictionRequestDTO.userId(),
                predictionRequestDTO.matchId(),
                predictionRequestDTO.predictionEntries().stream()
                        .map(PredictionEntryAdapter::toEntity)
                        .toList(),
                predictionRequestDTO.predictionTime(),
                0
        );
    }

    public static PredictionEvent toEvent(PredictionResponseDTO predictionResponseDTO) {
        return new PredictionEvent(
                predictionResponseDTO.id(),
                predictionResponseDTO.matchId(),
                predictionResponseDTO.userId(),
                predictionResponseDTO.predictionTime(),
                predictionResponseDTO.totalScore()
        );
    }
}
