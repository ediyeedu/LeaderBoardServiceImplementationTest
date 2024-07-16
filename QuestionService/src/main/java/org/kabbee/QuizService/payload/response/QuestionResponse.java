package org.kabbee.QuizService.payload.response;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kabbee.QuizService.models.QuestionScope;
import org.kabbee.QuizService.models.QuestionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {
    private Long id;
    private String value;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Enumerated(EnumType.STRING)
    private org.kabbee.QuizService.models.QuestionScope QuestionScope;

}
