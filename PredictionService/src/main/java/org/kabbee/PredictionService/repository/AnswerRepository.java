package org.kabbee.PredictionService.repository;



import org.kabbee.PredictionService.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByMatchId(Long matchId);
}
