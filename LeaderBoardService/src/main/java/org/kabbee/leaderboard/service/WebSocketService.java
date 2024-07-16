package org.kabbee.leaderboard.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.kabbee.leaderboard.dto.response.MatchBoardResponse;
import org.kabbee.leaderboard.dto.response.MatchLeaderBoard;
import org.kabbee.leaderboard.dto.response.TotalBoardResponse;
import org.kabbee.leaderboard.dto.response.TournamentLeaderBoard;
import org.kabbee.leaderboard.service.serviceImp.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate MessagingTemplate;
    private final ObjectMapper objectMapper;



    public void sendLeaderBoardUpdate(MatchLeaderBoard board){
        try{
            String message = objectMapper.writeValueAsString (board);
            System.out.println("&&&&&&&&******************************************Sending LeaderBoard Update: " + message);
            MessagingTemplate.convertAndSend ( "/topic/leaderBoard", message);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }


    public void sendTotalLeaderBoardUpdate(TournamentLeaderBoard board) {
        try {
            String message = objectMapper.writeValueAsString(board);
            System.out.println("*********************&&&&&&&&&&&&&&&&&&Sending Total LeaderBoard Update: " + message);
            MessagingTemplate.convertAndSend("/topic/totalLeaderBoard", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
