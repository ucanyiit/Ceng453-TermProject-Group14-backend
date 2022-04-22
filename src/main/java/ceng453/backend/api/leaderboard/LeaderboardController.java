package ceng453.backend.api.leaderboard;

import ceng453.backend.models.BaseResponse;
import ceng453.backend.models.leaderboardDTOs.authDTOs.AddScoreDTO;
import ceng453.backend.models.leaderboardDTOs.authDTOs.LeaderboardDTO;
import ceng453.backend.services.leaderboard.ILeaderboardService;
import io.swagger.annotations.ApiOperation;
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
     * @param leaderboardDTO: The leaderboardDTO object.
     * @return A sorted list of the scores for the given time period.
     */
    @ApiOperation(
            value = "Get Scores",
            notes = "Get the scores for a given time period",
            response = ResponseEntity.class
    )
    @GetMapping("")
    public ResponseEntity<BaseResponse> getLeaderboard(@RequestBody LeaderboardDTO leaderboardDTO) {
        return leaderboardService.getLeaderboard(leaderboardDTO.getStartDate(), leaderboardDTO.getEndDate());
    }

    /**
     * This method used to add a score for the user.
     * @param addScoreDTO: The addScoreDTO object.
     * @return A response with the status of the request.
     */
    @ApiOperation(
            value = "Add Score",
            notes = "Add a score with the username and score",
            response = ResponseEntity.class
    )
    @PostMapping("")
    public ResponseEntity<BaseResponse> addScore(@RequestBody AddScoreDTO addScoreDTO) {
        return leaderboardService.addScore(addScoreDTO.getUsername(), addScoreDTO.getScore());
    }
}
