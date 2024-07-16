package org.kabbee.PredictionService.service.messaging;



import java.util.List;



public record PredictionListEvent (
        List<PredictionEvent> predictionEvents
) {
}
