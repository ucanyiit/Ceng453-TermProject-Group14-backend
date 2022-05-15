package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.enums.GameType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class StartGameDTO {
    @ApiModelProperty(value = "The type of the game.", allowableValues = "SINGLEPLAYER, MULTIPLAYER")
    private GameType type;
}
