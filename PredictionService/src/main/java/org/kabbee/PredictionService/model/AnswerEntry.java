package org.kabbee.PredictionService.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@Data
public class AnswerEntry {

    private Long questionId;
    private String value;
}
