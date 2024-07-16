package org.kabbee.leaderboard.repository;


import org.kabbee.leaderboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CachedUserRepository extends JpaRepository<User,Long> {


    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    Optional<User> findUserByUserId( @Param ("userId") Long userId);
}
