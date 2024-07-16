package org.kabbee.leaderboard.events.sender;


import org.kabbee.leaderboard.dto.PrizeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LeaderBoardEvent {

    private static final String TOP_USERS_TOPIC = "prize";
    @Autowired
    private KafkaTemplate<String, PrizeEvent> kafkaTemplate;

    public void sendTopUsers(PrizeEvent message) {
        kafkaTemplate.send(TOP_USERS_TOPIC, message);

    }
}
