package org.kabbee.QuizService.payload.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kabbee.QuizService.models.QuestionScope;
import org.kabbee.QuizService.models.QuestionType;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionRequest {

    @NotEmpty(message = "Value can not empty")
    private String value;

    @NotNull(message = "Question type cannot be null")
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @NotNull(message = "QuestionScope cannot be null")
    @Enumerated(EnumType.STRING)
    private QuestionScope questionScope;

}
