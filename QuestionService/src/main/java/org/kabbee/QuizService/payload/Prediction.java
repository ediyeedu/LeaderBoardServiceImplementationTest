package org.kabbee.QuizService.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kabbee.QuizService.enums.QuizScope;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prediction {
    private Long userId;
    private int score;
    private QuizScope quizScope;
    private LocalDateTime timeStamp;
}
