package org.kabbee.PredictionService.service.adapter;


import org.kabbee.PredictionService.model.PredictionEntry;
import org.kabbee.PredictionService.service.dto.PredictionEntryRequestDTO;
import org.kabbee.PredictionService.service.dto.PredictionEntryResponseDTO;

public class PredictionEntryAdapter {

    public static PredictionEntryResponseDTO toDTO(PredictionEntry predictionEntry) {
        return new PredictionEntryResponseDTO(
                predictionEntry.getId(),
                predictionEntry.getQuestionId(),
                predictionEntry.getValue(),
                predictionEntry.getScore()
        );
    }

    public static PredictionEntry toEntity(PredictionEntryRequestDTO predictionEntryRequestDTO) {
        return new PredictionEntry(
                null,
                predictionEntryRequestDTO.questionId(),
                predictionEntryRequestDTO.value(),
                0
        );
    }
}
