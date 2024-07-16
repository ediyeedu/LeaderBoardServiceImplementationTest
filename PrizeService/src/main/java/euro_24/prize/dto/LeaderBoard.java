package euro_24.prize.dto;


import euro_24.prize.enums.QuizScope;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderBoard {

    private Long userId;
    private Integer score;
    private QuizScope quizScope;
    private LocalDateTime timeStamp;
}
