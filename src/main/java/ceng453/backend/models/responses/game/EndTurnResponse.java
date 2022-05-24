package ceng453.backend.models.responses.game;

import ceng453.backend.models.DTOs.game.BotActionDTO;
import ceng453.backend.models.DTOs.game.EndTurnDTO;
import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.services.game.TileService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@Getter
@Setter
public class EndTurnResponse extends BaseResponse {
    @ApiModelProperty(notes = "End turn structure consisting of game state and actions taken by bot")
    private EndTurnDTO response;


    public EndTurnResponse(boolean status, String message, EndTurnDTO response) {
        super(status, message);
        this.response = response;
    }

}
