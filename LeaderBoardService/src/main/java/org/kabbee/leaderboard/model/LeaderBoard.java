package org.kabbee.leaderboard.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LeaderBoard {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long userId;

    private LocalDateTime timeStamp;
    private double totalScore;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MatchEntry> matchEntries;




}
