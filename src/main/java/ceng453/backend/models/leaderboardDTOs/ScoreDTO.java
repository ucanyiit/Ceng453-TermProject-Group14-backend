package ceng453.backend.models.leaderboardDTOs;

import ceng453.backend.models.authDTOs.UsernameDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ScoreDTO extends UsernameDTO {
    @ApiModelProperty(value = "The score of a game.", example = "10")
    private Integer score;

    public ScoreDTO(String username, Integer score) {
        super(username);
        this.score = score;
    }
}
