package org.kabbee.leaderboard.repository;


import org.kabbee.leaderboard.model.MatchEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchEntryRepository extends JpaRepository<MatchEntry, Long> {

   @Query("SELECT e FROM MatchEntry e WHERE e.matchId = :matchId")
   List<MatchEntry> findByMatchId( Long matchId);
}
