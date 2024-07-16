package org.kabbee.QuizService.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kabbee.QuizService.payload.Prediction;
import org.kabbee.QuizService.payload.request.QuestionRequest;
import org.kabbee.QuizService.payload.request.QuizRequest;
import org.kabbee.QuizService.payload.response.QuizResponse;
import org.kabbee.QuizService.services.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuizController {




  @Autowired
  private final ObjectMapper objectMapper;

  private final QuizService quizService;

  private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
  
   @GetMapping("/healthcheck")
  public String healthCheck(){
    return "Quiz-service Working Fine";
  }

  @PostMapping("create")
  public ResponseEntity<?> createQuestion(@Valid @RequestBody QuizRequest quizRequest ) {

    return ResponseEntity.status( HttpStatus.CREATED).body(quizService.addQuiz(quizRequest));
  }


  @GetMapping("quiz-id/{quizId}")
  public ResponseEntity<?> getQuestionsByMatch(@PathVariable Long quizId) {
    return ResponseEntity.ok(quizService.findQuizById (quizId));
  }
  @PostMapping("update")
  public ResponseEntity<?> findQuiaDto( @RequestBody QuizRequest quizRequest){
    logger.info ( "From Quiz Controller ********************************" + quizRequest.getMatchId ());
    return ResponseEntity.status (HttpStatus.OK).body ( quizService.updateQuiz (quizRequest));

  }
   @GetMapping("quiz-match-id/{machId}")
   public ResponseEntity<?> findQuiaDto(@PathVariable("machId") Long machId){
     logger.info ( "From Quiz Controller ********************************" + machId);
    return ResponseEntity.status (HttpStatus.OK).body ( quizService.findQuizDtoByMatch (machId));

   }
  // Endpoint to get all questions
  @GetMapping("all-quiezes")
  public ResponseEntity<?> getAllquiezes() {
     List<QuizResponse> quizResponses = quizService.getAll ();
     if (quizResponses.isEmpty ()){
       return ResponseEntity.status (HttpStatus.OK).body ("No Quiz in Repository");
     }
    return ResponseEntity.status (HttpStatus.OK).body ( quizService.getAll ());
  }

  // Endpoint to get question by ID
  @GetMapping("quiz/{id}")
  public ResponseEntity<QuizResponse> getquizesById(@PathVariable("id") Long id) {
    return ResponseEntity.status ( HttpStatus.OK).body ( quizService.findQuizById (id));
  }



  // Endpoint to delete a question
  @DeleteMapping("delete/{matchId}")
  public ResponseEntity<?> deleteQuestion(@PathVariable("matchId") Long matchId) {
    return ResponseEntity.status (HttpStatus.OK).body ( quizService.deleteQuiz (matchId));
    }

    @DeleteMapping("clear")
  public ResponseEntity<?> deleteAllQuestions(){
    return ResponseEntity.status (HttpStatus.OK).body (quizService.deleteAll ());
    }



}
