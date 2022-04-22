package ceng453.backend.services.leaderboard;

import ceng453.backend.models.ResponseModels.BaseResponse;
import ceng453.backend.models.ResponseModels.Leaderboard.ScoresResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ILeaderboardService {
    /**
     * Get the scores of the top players between the startDate and endDate
     * @param startDate the start date of the leaderboard
     * @param endDate the end date of the leaderboard
     * @return the scores of the top players in sorted manner
     */
    ResponseEntity<BaseResponse> getLeaderboard(String startDate, String endDate);

    /**
     * Add a new score for the given user
     * @param username the username of the user
     * @param score the score of the user
     * @return the success message if the score is added successfully. Otherwise, the error message
     */
    ResponseEntity<BaseResponse> addScore(String username, Integer score);
}
