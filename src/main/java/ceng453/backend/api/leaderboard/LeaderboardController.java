package ceng453.backend.api.leaderboard;

import ceng453.backend.models.DTOs.leaderboard.ScoreDTO;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.services.leaderboard.ILeaderboardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/leaderboard")
@RequiredArgsConstructor
@RestController
public class LeaderboardController {

    // Injection
    private final ILeaderboardService leaderboardService;

    /**
     * This method used to fetch the leaderboard for a given a time period.
     *
     * @param startDate: The start date for scores.
     *                   The format is DD-MM-YYYY.
     * @param endDate:   The end date for scores.
     *                   The format is DD-MM-YYYY.
     * @return A sorted list of the scores for the given time period.
     */
    @ApiOperation(
            value = "Get Scores",
            notes = "Get the scores for a given time period",
            response = ResponseEntity.class
    )
    @GetMapping("")
    public ResponseEntity<BaseResponse> getLeaderboard(
            @ApiParam(value = "The unique username", example = "yigitucan")
            @RequestParam String startDate,
            @ApiParam(value = "The new password that contains more than 8 character", example = "yiityiit")
            @RequestParam String endDate) {
        return leaderboardService.getLeaderboard(startDate, endDate);
    }

    /**
     * This method used to add a score for the user.
     *
     * @param scoreDTO: The addScoreDTO object.
     * @return A response with the status of the request.
     */
    @ApiOperation(
            value = "Add Score",
            notes = "Add a score with the username and score",
            response = ResponseEntity.class
    )
    @PostMapping("")
    public ResponseEntity<BaseResponse> addScore(@RequestBody ScoreDTO scoreDTO) {
        return leaderboardService.addScore(scoreDTO.getUsername(), scoreDTO.getScore());
    }
}
