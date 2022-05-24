package ceng453.backend.models.responses.game;

import ceng453.backend.models.DTOs.game.BotActionDTO;
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
    @ApiModelProperty(notes = "Game State")
    private GameDTO game;

    @ApiModelProperty(notes = "The actions that the bot has taken with the dices")
    private List<BotActionDTO> botActions;

    public EndTurnResponse(boolean status, String message, GameDTO gameDTO, List<BotActionDTO> botActions) {
        super(status, message);
        this.game = gameDTO;
        this.botActions = botActions;
    }

}
