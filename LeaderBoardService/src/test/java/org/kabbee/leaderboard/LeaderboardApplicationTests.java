//package org.kabbee.leaderboard;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.kabbee.leaderboard.events.sender.LeaderBoardEvent;
//import org.kabbee.leaderboard.model.LeaderBoard;
//import org.kabbee.leaderboard.repository.LeaderBoardRepository;
//import org.kabbee.leaderboard.repository.MatchEntryRepository;
//import org.kabbee.leaderboard.service.LeaderBoardService;
//import org.kabbee.leaderboard.service.UserServiceClient;
//import org.kabbee.leaderboard.service.WebSocketService;
//import org.kabbee.leaderboard.service.serviceImp.LeaderBoardServiceImp;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBeans;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import static org.mockito.Mockito.when;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.verify;
//import static reactor.core.publisher.Mono.when;
//
//@ExtendWith(MockitoExtension.class)
//class LeaderboardApplicationTests {
//
//	@InjectMocks
//	LeaderBoardServiceImp leaderBoardService;
//	@Mock
//	private  LeaderBoardRepository leaderBoardRepository;
//	@Mock
//	private  MatchEntryRepository matchEntryRepository;
//	@Mock
//	private  WebSocketService webSocketService;
//	@Mock
//	private  UserServiceClient userServiceClient;
//	@Mock
//	private  LeaderBoardEvent leaderBoardEvent;

//	@BeforeEach
//	void setUp() {
//		leaderBoardService = new LeaderBoardServiceImp(
//				leaderBoardRepository, matchEntryRepository,
//				webSocketService,
//				userServiceClient,
//				leaderBoardEvent
//		);
//	}


//	@Test
//	void getAllBoardsShouldBeNonEmpty() {
//
//		List<LeaderBoard> leaderBoards = new ArrayList<>();
//		LeaderBoard leader1 =new LeaderBoard();
//		LeaderBoard leader2 =new LeaderBoard();
//		leaderBoards.add(leader1);
//		leaderBoards.add(leader2);
//
//		when(leaderBoardRepository.deleteAll()).thenReturn(leaderBoards);
//
//        var list = leaderBoardService.getAllLeaderBoards();
//		assertNotNull(list);
//		verify(leaderBoardService).getAllLeaderBoards();

//		Assertions.assertThrows(Exception.class, () -> {
//			 leaderBoardService.getAllLeaderBoards();
//		});
//	}



//}
package org.kabbee.leaderboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kabbee.leaderboard.dto.PredictionEvent;
import org.kabbee.leaderboard.dto.PrizeEvent;
import org.kabbee.leaderboard.dto.response.MatchBoardResponse;
import org.kabbee.leaderboard.events.sender.LeaderBoardEvent;
import org.kabbee.leaderboard.model.LeaderBoard;
import org.kabbee.leaderboard.model.MatchEntry;
import org.kabbee.leaderboard.model.User;
import org.kabbee.leaderboard.repository.LeaderBoardRepository;
import org.kabbee.leaderboard.repository.MatchEntryRepository;
import org.kabbee.leaderboard.service.UserServiceClient;
import org.kabbee.leaderboard.service.WebSocketService;
import org.kabbee.leaderboard.service.serviceImp.LeaderBoardServiceImp;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class LeaderboardApplicationTests {

	@InjectMocks
	LeaderBoardServiceImp leaderBoardService;

	@Mock
	private LeaderBoardRepository leaderBoardRepository;

	@Mock
	private MatchEntryRepository matchEntryRepository;

	@Mock
	private WebSocketService webSocketService;

	@Mock
	private UserServiceClient userServiceClient;

	@Mock
	private LeaderBoardEvent leaderBoardEvent;

	@BeforeEach
	void setUp() {
		leaderBoardService = new LeaderBoardServiceImp(
				leaderBoardRepository, matchEntryRepository,
				webSocketService,
				userServiceClient,
				leaderBoardEvent
		);
	}

	@Test
	void processPredictionsShouldWorkCorrectly() {
		List<PredictionEvent> predictions = new ArrayList<>();
		PredictionEvent predictionEvent = new PredictionEvent();
		predictionEvent.setUserId(1L);
		predictionEvent.setMatchId(1L);
		predictionEvent.setTotalScore(10.0);
		predictionEvent.setPredictionTime(LocalDateTime.now());
		predictions.add(predictionEvent);

		LeaderBoard leaderBoard = new LeaderBoard();
		leaderBoard.setUserId(1L);
		leaderBoard.setMatchEntries(new ArrayList<>());
		leaderBoard.setTotalScore(10.0);

		when(leaderBoardRepository.findByUserId(1L)).thenReturn(Optional.of(leaderBoard));
		when(leaderBoardRepository.save(any(LeaderBoard.class))).thenReturn(leaderBoard);

		leaderBoardService.processPredictions(predictions);

		verify(leaderBoardRepository, times(1)).save(any(LeaderBoard.class));
		verify(leaderBoardEvent, times(1)).sendTopUsers(any(PrizeEvent.class));
	}

	@Test
	void calculateAndSendLeaderboardUpdatesShouldWorkCorrectly() {
		Long matchId = 1L;
		List<MatchEntry> matchEntries = new ArrayList<>();
		MatchEntry matchEntry = new MatchEntry();
		matchEntry.setId(1L);
		matchEntry.setMatchId(matchId);
		matchEntry.setScore(10.0);
		matchEntries.add(matchEntry);

		LeaderBoard leaderBoard = new LeaderBoard();
		leaderBoard.setUserId(1L);
		leaderBoard.setMatchEntries(Collections.singletonList(matchEntry));
		leaderBoard.setTotalScore(10.0);

		when(matchEntryRepository.findByMatchId(matchId)).thenReturn(matchEntries);
		when(leaderBoardRepository.findByMatchEntries(1L)).thenReturn(Optional.of(leaderBoard));
		when(userServiceClient.getUserById(1L)).thenReturn(new User(1L,1L, "John", "Doe", "john.doe@example.com"));

		List<MatchBoardResponse> responses = leaderBoardService.calculateAndSendLeaderboardUpdates(matchId);

		assertNotNull(responses);
		assertFalse(responses.isEmpty());
		verify(webSocketService, times(1)).sendLeaderBoardUpdate(any());
	}

	@Test
	void getAllLeaderBoardsShouldReturnNonEmptyList() {
		List<LeaderBoard> leaderBoards = new ArrayList<>();
		LeaderBoard leader1 = new LeaderBoard();
		LeaderBoard leader2 = new LeaderBoard();
		leaderBoards.add(leader1);
		leaderBoards.add(leader2);

		when(leaderBoardRepository.findAll()).thenReturn(leaderBoards);

		var list = leaderBoardService.getAllLeaderBoards();
		assertNotNull(list);
		assertEquals(2, list.size());
		verify(leaderBoardRepository, times(1)).findAll();
	}
}
