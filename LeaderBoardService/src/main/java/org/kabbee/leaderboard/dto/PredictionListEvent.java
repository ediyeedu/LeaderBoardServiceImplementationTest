package org.kabbee.leaderboard.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionListEvent {

    @NotEmpty(message = "List of prediction event can not be null")
    private List<PredictionEvent> predictionEvents;
}
