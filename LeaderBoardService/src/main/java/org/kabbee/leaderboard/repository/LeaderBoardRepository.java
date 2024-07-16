package org.kabbee.leaderboard.repository;

import org.kabbee.leaderboard.model.LeaderBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaderBoardRepository extends JpaRepository<LeaderBoard, Long> {

    @Query("SELECT l FROM LeaderBoard l WHERE l.userId = :userId")
    Optional<LeaderBoard> findByUserId(@Param("userId") Long userId);

    @Query("SELECT lb FROM LeaderBoard lb JOIN lb.matchEntries me WHERE me.id = :id")
    Optional<LeaderBoard> findByMatchEntries(@Param ("id") Long id);

    //Added this method

    //List<LeaderBoard> getTopUsers();
}