package euro_24.prize.service.serviceImp;
import euro_24.prize.dto.PrizeDto;
import euro_24.prize.dto.PrizeEvent;
import euro_24.prize.dto.request.PrizeRequest;
import euro_24.prize.dto.response.PrizeResponse;
import euro_24.prize.enums.PrizeStatus;
import euro_24.prize.events.sender.EmailEvent;
import euro_24.prize.events.sender.NotificationEvent;
import euro_24.prize.model.Prize;
import euro_24.prize.repository.PrizeRepository;
import euro_24.prize.service.PrizeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class prizeServiceImp implements PrizeService {


    private static final Logger logger = LoggerFactory.getLogger(PrizeService.class);

    private final PrizeRepository prizeRepository;
    private final EmailEvent emailEvent;
    private final NotificationEvent notificationEvent;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void processPrizeEvent(PrizeEvent prizeEvent) {
        List<Prize> matchPrizes = prizeEvent.getMatchBoardResponses().stream()
                .map(response -> createPrize(response.getUserId(), response.getMatchId(), response.getFirstName(),
                        response.getLastname(),response.getEmail (), response.getScore(), response.getRank(), "MATCH"))
                .collect(Collectors.toList());

        List<Prize> totalPrizes = prizeEvent.getTotalBoardResponses().stream()
                .map(response -> createPrize(response.getUserId(), response.getMatchId(), response.getFirstName(),
                        response.getLastname() ,response.getEmail (), response.getTotalScore(), response.getRank(), "TOTAL"))
                .collect(Collectors.toList());

        prizeRepository.saveAll(matchPrizes);
        prizeRepository.saveAll(totalPrizes);
    }

    private Prize createPrize(Long userId, Long matchId, String firstName, String lastName,String email, double score, int rank, String prizeType) {
        return Prize.builder()
                .userId(userId)
                .matchId(matchId)
                .firstName(firstName)
                .lastName(lastName)
                .email (email)
                .score(score)
                .rank(rank)
                .prizeType(prizeType)
                .prizeDescription(prizeType.equals("MATCH") ? getMatchPrizeDescription(rank) : getTotalPrizeDescription(rank))
                .prizeStatus(PrizeStatus.PENDING)
                .build();
    }

    private String getMatchPrizeDescription(int rank) {
        switch (rank) {
            case 1:
                return "$200";
            case 2:
                return "$100";
            case 3:
                return "Gift Card";
            default:
                return "Participant";
        }
    }

    private String getTotalPrizeDescription(int rank) {
        switch (rank) {
            case 1:
                return "Mobile";
            case 2:
                return "$200";
            case 3:
                return "Gift Card";
            default:
                return "Participant";
        }
    }

    @Override
    public List<PrizeDto> getAllPendingPrizes() {
        return prizeRepository.findByStatus(PrizeStatus.PENDING)
                .stream()
                .map(prize -> modelMapper.map(prize,PrizeDto.class))
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public List<PrizeResponse> getTopWinnersMatchId(Long matchId) {
        return prizeRepository.findByPrizeTypeAndMatchIdAndPrizeStatus(  "MATCH", matchId, PrizeStatus.PENDING).stream()
                .filter(prize -> prize.getRank() == 1 || prize.getRank() == 2 || prize.getRank() == 3)
                .map(this::convertToPrizeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PrizeResponse> getTopTotalWinners() {
        return prizeRepository.findByPrizeTypeAndStatus("TOTAL", PrizeStatus.PENDING).stream()
                .filter(prize -> prize.getRank() == 1 || prize.getRank() == 2 || prize.getRank() == 3)
                .map(this::convertToPrizeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PrizeDto>  confirmTopWinnersByMatchId(Long matchId) {
        List<Prize> topPrizes = prizeRepository.findByPrizeTypeAndMatchIdAndPrizeStatus("MATCH", matchId, PrizeStatus.PENDING).stream()
                .filter(prize -> prize.getRank() == 1 || prize.getRank() == 2 || prize.getRank() == 3)
                .peek(prize -> {
                    prize.setPrizeStatus(PrizeStatus.CONFIRMED);
                    prize.setConfirmedAt(LocalDateTime.now());
                })
                .collect(Collectors.toList());

        prizeRepository.saveAll(topPrizes);

        List<PrizeDto>  prizeDtos = topPrizes.stream ().map(prize ->
                modelMapper.map (prize,PrizeDto.class)).collect( Collectors.toList());
        for(PrizeDto prizeDto: prizeDtos){
            notificationEvent.sendNotification(prizeDto);
            emailEvent.sendEmail(prizeDto);
        }

        topPrizes.forEach(prize -> {
//            notificationEvent.sendNotification(prize);
//            emailEvent.sendEmail(prize);
        });

        return prizeDtos;
    }

    @Override
    @Transactional
    public String confirmTopTotalWinners() {

        List<Prize> topPrizes = prizeRepository.findByPrizeTypeAndStatus("TOTAL", PrizeStatus.PENDING).stream()
                .filter(prize -> prize.getRank() == 1 || prize.getRank() == 2 || prize.getRank() == 3)
                .peek(prize -> {
                    prize.setPrizeStatus(PrizeStatus.CONFIRMED);
                    prize.setConfirmedAt(LocalDateTime.now());
                })
                .collect(Collectors.toList());

        if (topPrizes.isEmpty()) {

            return "No pending total prizes found for the top 3 ranks.";
        }

        prizeRepository.saveAll(topPrizes);

        for(Prize prize: topPrizes){
            System.out.println ("prize");
        }
        logger.info("Top total prizes confirmed.");

        List<PrizeDto>  prizeDtos = topPrizes.stream ().map(prize ->
                modelMapper.map (prize,PrizeDto.class)).collect( Collectors.toList());
          for(PrizeDto prizeDto: prizeDtos){
              logger.info("Sending notification and email for prize ID: {}", prizeDto.getFirstName ());
              notificationEvent.sendNotification(prizeDto);
              emailEvent.sendEmail(prizeDto);
              System.out.println ("******88888888*******8888888888********888888888********888888****8" + prizeDto);
          }


//            notificationEvent.sendNotification(prize);
//            emailEvent.sendEmail(prize);
        return "Prizes Confirmed";
    }

    @Override
    public List<PrizeResponse> getUserPrizes(Long userId) {
        return prizeRepository.findByUserId(userId)
                .stream()
                .map(this::convertToPrizeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PrizeResponse getPrizeById(Long id) {
        Optional<Prize> prize = prizeRepository.findById(id);
        return prize.map(this::convertToPrizeResponse).orElse(null);
    }

    @Override
    public List<PrizeResponse> getAllPrizes() {
        return prizeRepository.findAll()
                .stream()
                .map(this::convertToPrizeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrizeResponse> savePrizes(List<PrizeRequest> prizeRequests) {
        List<Prize> prizes = prizeRequests.stream()
                .map(this::mapPrizeRequestToPrize)
                .collect(Collectors.toList());

        prizeRepository.saveAll(prizes);

        return prizes.stream()
                .map(this::convertToPrizeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteAll() {
         prizeRepository.deleteAll ();
        return "All Prize's Deleted Successfully";
    }


    private Prize mapPrizeRequestToPrize(PrizeRequest request) {
        Prize prize = modelMapper.map(request, Prize.class);
        prize.setConfirmedAt(LocalDateTime.now());
        return prize;
    }


    private PrizeResponse convertToPrizeResponse(Prize prize) {
        return modelMapper.map(prize, PrizeResponse.class);
    }


}
