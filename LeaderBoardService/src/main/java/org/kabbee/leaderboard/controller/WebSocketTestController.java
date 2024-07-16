package org.kabbee.leaderboard.controller;


import lombok.RequiredArgsConstructor;
import org.kabbee.leaderboard.dto.response.MatchBoardResponse;
import org.kabbee.leaderboard.dto.response.TotalBoardResponse;
import org.kabbee.leaderboard.service.WebSocketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("websocket")
public class WebSocketTestController {

    private final WebSocketService webSocketService;

    @GetMapping("/send-leaderboard-update")
    public String sendLeaderBoardUpdate() {
//        MatchBoardResponse response = new MatchBoardResponse("John", "Doe", 100, 1);
//        webSocketService.sendLeaderBoardUpdate(response);
        return "Leaderboard update sent!";
    }

    @GetMapping("/send-total-leaderboard-update")
    public String sendTotalLeaderBoardUpdate() {
//        TotalBoardResponse response = new TotalBoardResponse("Jane", "Doe", 200, 1);
//        webSocketService.sendTotalLeaderBoardUpdate(response);
        return "Total leaderboard update sent!";
    }
}
