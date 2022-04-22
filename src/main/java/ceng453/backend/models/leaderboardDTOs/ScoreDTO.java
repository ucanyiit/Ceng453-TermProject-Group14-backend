package ceng453.backend.models.leaderboardDTOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreDTO {
    @ApiModelProperty(value = "The username of the player that got the score.", example = "yigitucan")
    private String username;

    @ApiModelProperty(value = "The score of the user for the game.", example = "10")
    private Integer score;
}
