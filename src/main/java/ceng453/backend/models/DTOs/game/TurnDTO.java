package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.enums.GameType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;
import java.util.Random;

@Getter
public class TurnDTO {
    @ApiModelProperty(notes = "Game ID")
    private int gameId;

    public TurnDTO(int gameId) {
        this.gameId = gameId;
    }


}
