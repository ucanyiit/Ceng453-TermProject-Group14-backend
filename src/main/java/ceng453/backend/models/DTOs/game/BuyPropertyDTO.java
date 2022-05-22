package ceng453.backend.models.DTOs.game;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class BuyPropertyDTO {
    @ApiModelProperty(notes = "Game ID")
    private int gameId;
    @ApiModelProperty(notes = "Propery Location")
    private int location;

    public BuyPropertyDTO(int gameId, int location) {
        this.gameId = gameId;
        this.location = location;
    }
}
