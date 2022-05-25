package ceng453.backend.models.DTOs.leaderboard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LeaderboardScoreDTO extends ScoreDTO {
    @ApiModelProperty(value = "The date of the game.", example = "10/10/2020")
    private String date;

    public LeaderboardScoreDTO(String username, Double score, String date) {
        this.setUsername(username);
        this.setScore(score);
        this.date = date;
    }
}
