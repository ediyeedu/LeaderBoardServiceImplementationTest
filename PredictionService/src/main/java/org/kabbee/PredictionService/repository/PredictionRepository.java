package org.kabbee.PredictionService.repository;


import org.kabbee.PredictionService.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    List<Prediction> findAllByMatchId(Long matchId);
}
