package euro_24.prize.service;

import euro_24.prize.dto.PrizeDto;
import euro_24.prize.dto.PrizeEvent;
import euro_24.prize.dto.request.PrizeRequest;
import euro_24.prize.dto.response.PrizeResponse;

import java.util.List;


public interface PrizeService {

    void processPrizeEvent(PrizeEvent prizeEvent);

    List<PrizeDto> getAllPendingPrizes();

    List<PrizeResponse> getTopWinnersMatchId(Long matchId);

    List<PrizeResponse> getTopTotalWinners();

    List<PrizeDto> confirmTopWinnersByMatchId(Long matchId);

    String confirmTopTotalWinners();

    List<PrizeResponse> getUserPrizes(Long userId);

    PrizeResponse getPrizeById(Long id);

    List<PrizeResponse> getAllPrizes();

    List<PrizeResponse> savePrizes(List<PrizeRequest> prizeRequests);


    public String deleteAll();


}
