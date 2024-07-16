package org.kabbee.QuizService.payload.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizRequest {

 @NotNull(message = "Match ID cannot be null")
  private Long matchId;

  @NotEmpty(message = "Question list cannot be empty")
  List<@Valid QuestionRequest>  questions;
}
