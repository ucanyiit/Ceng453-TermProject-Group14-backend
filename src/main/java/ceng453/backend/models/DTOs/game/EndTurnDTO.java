package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.enums.ActionType;
import ceng453.backend.services.game.TileService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@Getter
@Setter
public class EndTurnDTO {
    @ApiModelProperty(notes = "Game ID")
    private int gameId;

    @ApiModelProperty(notes = "Game State")
    private GameDTO game;

    @ApiModelProperty(notes = "The actions that the bot has taken with the dices")
    private List<BotActionDTO> botActions;


    public EndTurnDTO(int gameId, GameDTO gameDTO, List<BotActionDTO> botActions) {
        this.gameId = gameId;
        this.game = gameDTO;
        this.botActions = botActions;
    }


}
