package org.kabbee.QuizService.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kabbee.QuizService.models.QuestionScope;
import org.kabbee.QuizService.models.QuestionType;
import org.kabbee.QuizService.payload.request.QuestionRequest;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quizdto {
    private Long matchId;
    private List<QuestionResponse> questions;

}
