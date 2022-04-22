package ceng453.backend.models.ResponseModels.Leaderboard;


import ceng453.backend.models.ResponseModels.BaseResponse;
import ceng453.backend.models.leaderboardDTOs.LeaderboardScoreDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScoresResponse extends BaseResponse {
    public List<LeaderboardScoreDTO> response;

    /**
     * @param response Scores list.
     */
    public ScoresResponse(boolean status, String message, List<LeaderboardScoreDTO> response) {
        super(status, message);
        this.response = response;
    }

}

