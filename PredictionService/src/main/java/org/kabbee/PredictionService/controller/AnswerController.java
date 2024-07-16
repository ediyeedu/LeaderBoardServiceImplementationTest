package org.kabbee.PredictionService.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.kabbee.PredictionService.service.messaging.PredictionEvent;
import org.kabbee.PredictionService.service.AnswerService;
import org.kabbee.PredictionService.service.dto.AnswerRequestDto;
import org.kabbee.PredictionService.service.dto.AnswerResponseDto;
import org.kabbee.PredictionService.service.messaging.PredictionListEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prediction-service/api/v1/answers")
public class AnswerController {
    private final AnswerService answerService;



    @GetMapping("/healthcheck")
    public String healthCheck(){
        return "Prediction service working Fine";
    }
    
    @GetMapping("all")
    public ResponseEntity<?> getAllAnswers() {
        return ResponseEntity.ok().body(answerService.getAllAnswers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnswer(@PathVariable Long id) {
        AnswerResponseDto answerResponseDto = answerService.getAnswerById(id);
        if (answerResponseDto == null) {
            return new ResponseEntity<>("Answer Not Found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(answerResponseDto);
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<?> getAnswerByMatchId(@PathVariable Long matchId) {
        AnswerResponseDto answerResponseDto = answerService.getAnswerByMatchId(matchId);
        if (answerResponseDto == null) {
            return new ResponseEntity<>("Answer Not Found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(answerResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<?> createAnswer(@RequestBody AnswerRequestDto answerRequestDto) {

        return ResponseEntity.ok().body(answerService.saveAnswer(answerRequestDto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long id, @RequestBody AnswerRequestDto answerRequestDto) {
        AnswerResponseDto answerResponseDto = answerService.updateAnswer(id, answerRequestDto);
        if (answerResponseDto == null) {
            throw new RuntimeException("Answer Not Found");
        }
        return ResponseEntity.ok().body(answerResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.ok().body("Answer deleted successfully");
    }
}
