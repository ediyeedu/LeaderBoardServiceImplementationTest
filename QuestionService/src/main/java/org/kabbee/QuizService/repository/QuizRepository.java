package org.kabbee.QuizService.repository;


import org.kabbee.QuizService.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    Optional<Quiz> findByMatchId(Long matchId);

    public String deleteQuizByMatchId(Long matchId);
}

