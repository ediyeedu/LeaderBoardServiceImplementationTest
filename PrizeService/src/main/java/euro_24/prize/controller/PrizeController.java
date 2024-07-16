package euro_24.prize.controller;

import euro_24.prize.dto.PrizeDto;
import euro_24.prize.dto.PrizeEvent;
import euro_24.prize.dto.request.PrizeRequest;
import euro_24.prize.dto.response.PrizeResponse;
import euro_24.prize.service.PrizeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


@RestController
@RequestMapping("prize-service/prize")
@RequiredArgsConstructor
public class PrizeController {


    private static final Logger logger = LoggerFactory.getLogger(PrizeController.class);

    private final PrizeService prizeService;


 @GetMapping("/healthcheck")
    public String healthCheck(){
        return "Prize-service working Fine";
    }
    
    @PostMapping("/process")
    public ResponseEntity<?> processPrizeEvent(@RequestBody PrizeEvent prizeEvent) {
        prizeService.processPrizeEvent(prizeEvent);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingPrizes() {
        List<PrizeDto> prizes = prizeService.getAllPendingPrizes();
        return ResponseEntity.status(HttpStatus.OK).body(prizes);
    }

    @GetMapping("/top-match-winners/{matchId}")
    public ResponseEntity<?> getTopWinnersMatchId( @PathVariable Long matchId) {
        List<PrizeResponse> prizes = prizeService.getTopWinnersMatchId(matchId);
        return ResponseEntity.status(HttpStatus.OK).body(prizes);
    }

    @GetMapping("/top-total-winners")
    public ResponseEntity<?> getTopTotalWinners() {
        List<PrizeResponse> prizes = prizeService.getTopTotalWinners();
        return ResponseEntity.status(HttpStatus.OK).body(prizes);
    }

    @PostMapping("/confirm-match-winners/{matchId}")
    public ResponseEntity<?> confirmTopWinnersByMatchId(@PathVariable Long matchId) {

        return ResponseEntity.status(HttpStatus.OK).body( prizeService.confirmTopWinnersByMatchId(matchId));
    }

    @GetMapping("/confirm-total-winners")
    public ResponseEntity<?> confirmTopTotalWinners() {

        String result = prizeService.confirmTopTotalWinners();

        if (result.equals("No pending total prizes found for the top 3 ranks.")) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user-prizes")
    public ResponseEntity<?> getUserPrizes(@RequestParam Long userId) {
        List<PrizeResponse> prizes = prizeService.getUserPrizes(userId);
        return ResponseEntity.status(HttpStatus.OK).body(prizes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrizeById(@PathVariable Long id) {
        PrizeResponse prize = prizeService.getPrizeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(prize);
    }

    @GetMapping("all-prizes")
    public ResponseEntity<?> getAllPrizes() {
        List<PrizeResponse> prizes = prizeService.getAllPrizes();

           if(prizes.isEmpty ()){
            String message = "No prize In Repository";
            return ResponseEntity.status(HttpStatus.OK).body(message);
            }

        return ResponseEntity.status(HttpStatus.OK).body(prizes);
    }

    @PostMapping("save")
    public ResponseEntity<?> savePrizes(@RequestBody List<PrizeRequest> prizeRequests) {
        List<PrizeResponse> prizes = prizeService.savePrizes(prizeRequests);
        return ResponseEntity.status(HttpStatus.OK).body(prizes);
    }

    @DeleteMapping("clear")
    public ResponseEntity<?> deletePrizes(){
        return ResponseEntity.status (HttpStatus.OK).body (prizeService.deleteAll ());
    }
}
