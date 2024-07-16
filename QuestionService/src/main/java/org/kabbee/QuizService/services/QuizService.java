package org.kabbee.QuizService.services;

import java.util.List;

import org.kabbee.QuizService.models.Question;
import org.kabbee.QuizService.payload.request.QuestionRequest;
import org.kabbee.QuizService.payload.request.QuizRequest;
import org.kabbee.QuizService.payload.response.QuizResponse;
import org.kabbee.QuizService.payload.response.Quizdto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface QuizService {

  public QuizResponse findQuizById(Long id);

  public String addQuiz(QuizRequest quizRequest);
  public QuizResponse findQuizByMachId(Long matchId);
  public List<QuizResponse> getAll();
  public String updateQuiz(QuizRequest quizRequest);
  public String deleteQuiz(Long matchId);
   public String deleteAll();

  public Quizdto findQuizDtoByMatch(Long matchId);




}
