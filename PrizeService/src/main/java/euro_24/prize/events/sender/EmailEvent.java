package euro_24.prize.events.sender;


import euro_24.prize.dto.PrizeDto;
import euro_24.prize.model.Prize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class EmailEvent {

    @Autowired
    private KafkaTemplate<String, PrizeDto> kafkaTemplate;


    public void sendEmail(PrizeDto prizeDto){
        log.info ( "Inside send method of PrizeEvent"  + prizeDto.getFirstName ());
        kafkaTemplate.send ( "email", prizeDto );
    }
}
