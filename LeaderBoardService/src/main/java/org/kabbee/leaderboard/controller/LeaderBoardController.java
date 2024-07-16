package org.kabbee.leaderboard.controller;


import lombok.RequiredArgsConstructor;
import org.kabbee.leaderboard.dto.PredictionEvent;
import org.kabbee.leaderboard.events.sender.LeaderBoardEvent;
import org.kabbee.leaderboard.model.LeaderBoard;
import org.kabbee.leaderboard.repository.LeaderBoardRepository;
import org.kabbee.leaderboard.service.LeaderBoardService;
import org.kabbee.leaderboard.service.serviceImp.LeaderBoardServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("leaderboard-service/leaderboard")
@RequiredArgsConstructor
public class LeaderBoardController {



    private final LeaderBoardService leaderBoardService;
    private final LeaderBoardRepository repository;
    @Value ( "${spring.kafka.bootstrap-servers}" )
    private String bootStrapServer;
    @GetMapping("/healthcheck")
    public String healthCheck(){
        return "LeaderBoard service working Fine" + "With Kafka Url:" + bootStrapServer;
    }
    
    @GetMapping("hello")
    public  String hello(){
        return "Hello";
    }

    @GetMapping("/all")
    public ResponseEntity<List<LeaderBoard>> getAllLeaderBoards() {
        List<LeaderBoard> leaderBoards = leaderBoardService.getAllLeaderBoards();
        return ResponseEntity.ok(leaderBoards);
    }



//
//    @PostMapping("saveAll")
//    public  ResponseEntity<?>  saveAll(@RequestBody  List<Prediction> prediction){
//        return ResponseEntity.status(HttpStatus.OK).body(leader.saveAll(prediction));
//    }
//
//    @PutMapping("/{id}/updated/")
//    public LeaderBoard updateLeaderBoardEntry(@PathVariable long id, @RequestBody LeaderBoard leaderBoard) {
//        return leaderBoardService.updateLeaderBoardEntry(id, leaderBoard );
//    }
//
//    @DeleteMapping("/{id}")
//    public void removeLeaderBoardEntry(@PathVariable long id) {
//
//    }
//
//    @PostMapping("/send")
//    public void sendMessageToKafka(@RequestBody String message) {
//        leaderBoardEvent.sendTopUsers(message);
//
//    }
}
