package org.kabbee.QuizService.services.serviceImp;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kabbee.QuizService.exceptions.ResourceAlreadyExistsException;
import org.kabbee.QuizService.exceptions.ResourceNotFoundException;
import org.kabbee.QuizService.models.Question;
import org.kabbee.QuizService.models.Quiz;
import org.kabbee.QuizService.payload.request.QuestionRequest;
import org.kabbee.QuizService.payload.request.QuizRequest;
import org.kabbee.QuizService.payload.response.QuestionResponse;
import org.kabbee.QuizService.payload.response.QuizResponse;
import org.kabbee.QuizService.payload.response.Quizdto;
import org.kabbee.QuizService.repository.QuizRepository;
import org.kabbee.QuizService.services.QuizService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizServiceImp  implements QuizService {


    private final QuizRepository quizRepository;

    private final ModelMapper modelMapper;

//    Admin finds quiz by id
    @Override
    public QuizResponse findQuizById(Long id) {

        Quiz quiz = quizRepository.findById (id).orElseThrow (()->
                new ResourceNotFoundException ("Quiz with id: " + id + " does not exist"));
        return modelMapper.map (quiz, QuizResponse.class);
    }

//    Admin create quiz or  question
    @Override
    public String addQuiz(QuizRequest quizRequest) {
        if(quizRepository.findByMatchId (quizRequest.getMatchId ()).isPresent ()){
            throw new ResourceAlreadyExistsException ("Quiz with matchId: " + quizRequest.getMatchId() + " already exists");
        }
        quizRepository.save ( modelMapper.map (quizRequest, Quiz.class ));
        return "Quiz Added Successfully";
    }


// questions to be answered by user

     @Override
    public QuizResponse findQuizByMachId(Long matchId) {
        Quiz quiz =  quizRepository.findByMatchId (matchId).orElseThrow (()->
                new ResourceNotFoundException ("Quiz with MatchId:" + " " + matchId + "Does not exist"));
        return modelMapper.map (quiz,QuizResponse.class  );
    }

//    Admin checks all the available quiz's
    @Override
    public List<QuizResponse> getAll() {
        List<QuizResponse> quizResponses = quizRepository.findAll().stream().map(quiz -> {
            QuizResponse response = modelMapper.map(quiz, QuizResponse.class);
            // Ensure that the questions are also mapped correctly
            List<QuestionResponse> questions = quiz.getQuestions().stream()
                    .map(question -> modelMapper.map(question, QuestionResponse.class))
                    .collect(Collectors.toList());
            response.setQuestions(questions);
            return response;
        }).collect(Collectors.toList());

        return quizResponses;
    }

// Admin update the quiz's
@Override
public String updateQuiz(QuizRequest quizRequest) {
    Quiz quiz = quizRepository.findByMatchId(quizRequest.getMatchId ()).orElseThrow(() ->
            new ResourceNotFoundException ("Quiz with that matchId: " + quizRequest.getMatchId () + " Not Found"));

    List<Question> questions = quizRequest.getQuestions ().stream ().map (question
            -> modelMapper.map (question,Question.class)).collect( Collectors.toList());
    quiz.setQuestions (questions);
    // Save the updated quiz
    quizRepository.save(quiz);

   return "Quiz Updated Successfully";
}


    @Override
    public String deleteQuiz(Long matchId) {
        if(matchId==null){
            throw new ResourceNotFoundException ("Quiz With matchId:" + " " + matchId + "not found");
        }
        quizRepository.deleteQuizByMatchId (matchId);
        return " Quiz deleted successfully";
    }

    @Override
    public String deleteAll() {
        quizRepository.deleteAll ();
        return "All Quiz's Deleted successfully";
    }

    @Override
    public Quizdto findQuizDtoByMatch(Long matchId) {
        Quiz quiz = quizRepository.findByMatchId(matchId).orElseThrow(() ->
                new RuntimeException("Quiz with MatchId: " + matchId + " Not Found"));

        Quizdto quizdto = new Quizdto();
        quizdto.setMatchId(quiz.getMatchId());

        List<QuestionResponse> questionResponses = quiz.getQuestions().stream()
                .map(question -> new QuestionResponse(
                        question.getId (),
                        question.getValue(),
                        question.getType(),
                        question.getQuestionScope()
                ))
                .collect(Collectors.toList());

        quizdto.setQuestions(questionResponses);

        return quizdto;
    }




}
