package euro_24.prize.repository;

import euro_24.prize.enums.PrizeStatus;
import euro_24.prize.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrizeRepository extends JpaRepository<Prize,Long> {

    @Query("SELECT p FROM Prize p WHERE p.prizeStatus = :status")
    List<Prize> findByStatus(@Param("status") PrizeStatus status);

    @Query("SELECT p FROM Prize p WHERE p.userId = :userId")
    List<Prize> findByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Prize p WHERE p.prizeType = :prizeType AND p.prizeStatus = :prizeStatus")
    List<Prize> findByPrizeTypeAndStatus(@Param("prizeType") String prizeType, @Param("prizeStatus") PrizeStatus prizeStatus);

    @Query("SELECT p FROM Prize p WHERE p.prizeType = :prizeType AND p.matchId = :matchId AND p.prizeStatus = :status")
    List<Prize> findByPrizeTypeAndMatchIdAndPrizeStatus(@Param("prizeType") String prizeType,
                                                             @Param("matchId") Long matchId, @Param ("status") PrizeStatus status);

    @Query("SELECT p FROM Prize p WHERE p.matchId = :matchId AND p.prizeStatus = :prizeStatus")
    List<Prize> findByMatchIdAndPrizeStatus(@Param("matchId") Long matchId, @Param("prizeStatus") PrizeStatus prizeStatus);
}
