package ceng453.backend.models.leaderboardDTOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LeaderboardScoreDTO extends ScoreDTO {
    @ApiModelProperty(value = "The date of the game.", example = "10/10/2020")
    private String date;

    public LeaderboardScoreDTO(String username, Integer score, String date) {
        super(username, score);
        this.date = date;
    }
}
