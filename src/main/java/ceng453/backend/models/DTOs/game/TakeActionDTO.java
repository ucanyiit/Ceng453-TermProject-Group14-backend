package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.enums.ActionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class TakeActionDTO {
    @ApiModelProperty(notes = "Game ID")
    private int gameId;
    @ApiModelProperty(notes = "Action Type")
    private ActionType actionType;

    public TakeActionDTO(int gameId, ActionType actionType) {
        this.gameId = gameId;
        this.actionType = actionType;
    }


}
