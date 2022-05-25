package ceng453.backend.models.responses.game;

import ceng453.backend.models.DTOs.game.NextTurnDTO;
import ceng453.backend.models.responses.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndTurnResponse extends BaseResponse {
    @ApiModelProperty(notes = "End turn structure consisting of game state and actions taken by bot")
    private NextTurnDTO response;


    public EndTurnResponse(boolean status, String message, NextTurnDTO response) {
        super(status, message);
        this.response = response;
    }

}
