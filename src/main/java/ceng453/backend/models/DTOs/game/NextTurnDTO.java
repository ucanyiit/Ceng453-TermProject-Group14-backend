package ceng453.backend.models.DTOs.game;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NextTurnDTO {
    @ApiModelProperty(notes = "Game ID")
    private int gameId;

    @ApiModelProperty(notes = "Game State")
    private GameDTO game;

    @ApiModelProperty(notes = "The actions that the bot has taken with the dices")
    private BotActionDTO botAction;


    public NextTurnDTO(int gameId, GameDTO gameDTO, BotActionDTO botAction) {
        this.gameId = gameId;
        this.game = gameDTO;
        this.botAction = botAction;
    }


}
