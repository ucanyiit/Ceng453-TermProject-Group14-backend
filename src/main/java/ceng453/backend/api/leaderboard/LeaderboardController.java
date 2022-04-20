package ceng453.backend.api.leaderboard;

import ceng453.backend.models.BaseResponse;
import ceng453.backend.services.leaderboard.ILeaderboardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/leaderboard")
@RequiredArgsConstructor
@RestController
public class LeaderboardController {

    // Injection
    private final ILeaderboardService leaderboardService;

    /**
     * This method used to fetch the leaderboard for a given a time period.
     * @param startDate: The start date for the leaderboard.
     * @param endDate: The end date for the leaderboard.
     * @return A sorted list of the scores for the given time period.
     */
    @ApiOperation(
            value = "Get Scores",
            notes = "Get the scores for a given time period",
            response = ResponseEntity.class
    )
    @GetMapping("/get-scores")
    public ResponseEntity<BaseResponse> getLeaderboard(String startDate, String endDate) {
        return leaderboardService.getLeaderboard(startDate, endDate);
    }

    /**
     * This method used to add a score for the user.
     * @param username: The username of the user.
     * @param score: The score of the user.
     * @return A response with the status of the request.
     */
    @ApiOperation(
            value = "Add Score",
            notes = "Add a score with the username and score",
            response = ResponseEntity.class
    )
    @PostMapping("/add-score")
    public ResponseEntity<BaseResponse> getLeaderboard(String username, Integer score) {
        return leaderboardService.addScore(username, score);
    }
}
