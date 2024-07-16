package org.kabbee.leaderboard.events.listener;


import jakarta.validation.Valid;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kabbee.leaderboard.dto.PredictionEvent;
import org.kabbee.leaderboard.dto.PredictionListEvent;
import org.kabbee.leaderboard.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PredictionListener {

    private final LeaderBoardService leaderBoardService;

    private static final Logger logger = LoggerFactory.getLogger(PredictionListener.class);



    @KafkaListener(topics = "prediction", groupId = "prediction-group")
    public void predictionListener(@Valid PredictionListEvent predictionListEventEvent) {

        logger.info("Received message in predictionListener**************8888888*********88888******88888*******");
        List<PredictionEvent> predictions = predictionListEventEvent.getPredictionEvents();

        if (predictions != null && !predictions.isEmpty()) {
            logger.info("Predictions received: {}", predictions);
            leaderBoardService.processPredictions(predictions);
        } else {
            logger.warn("No predictions received or the list is empty");
        }


    }

}