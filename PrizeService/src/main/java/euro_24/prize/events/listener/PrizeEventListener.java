package euro_24.prize.events.listener;


import euro_24.prize.dto.PrizeEvent;
import euro_24.prize.service.PrizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrizeEventListener {

    private final PrizeService prizeService;

    @KafkaListener(topics = "prize", groupId = "prize-group")
    public void handlePrizeEvent(PrizeEvent prizeEvent) {
        System.out.println ("%%%%%%%%999999999999%%%%%%%%%%%%%9999999999999%%%%%%%%%%%%999999999999999%%%" + prizeEvent);
        prizeService.processPrizeEvent(prizeEvent);
    }
}
