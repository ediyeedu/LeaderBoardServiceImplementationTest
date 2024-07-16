package org.kabbee.PredictionService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class PredictionEntry {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Long questionId;
        private String value;
        private double score;


}
