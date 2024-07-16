package euro_24.prize.dto;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrizeEvent {

    private List<MatchBoardResponse> matchBoardResponses;
    private List<TotalBoardResponse> totalBoardResponses;
}
