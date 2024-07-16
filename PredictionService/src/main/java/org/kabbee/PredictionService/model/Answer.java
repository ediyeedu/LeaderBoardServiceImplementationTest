package org.kabbee.PredictionService.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
@Table(name = "answers")
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long matchId;
    @ElementCollection
    private List<AnswerEntry> answerEntries;

    public AnswerEntry getAnswerEntryByQuestionId(Long questionId) {
        AnswerEntry answerEntry = null;
        for(AnswerEntry entry : answerEntries) {
            if(Objects.equals(entry.getQuestionId(), questionId)) {
                answerEntry = entry;
            }
        }
        return answerEntry;
    }

}
