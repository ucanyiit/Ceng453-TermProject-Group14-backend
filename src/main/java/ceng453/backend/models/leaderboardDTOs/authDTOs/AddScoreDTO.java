package ceng453.backend.models.leaderboardDTOs.authDTOs;

import ceng453.backend.models.authDTOs.UsernameDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class AddScoreDTO extends UsernameDTO {
    @ApiModelProperty(value = "The score of a game.", example = "10")
    private Integer score;
}
