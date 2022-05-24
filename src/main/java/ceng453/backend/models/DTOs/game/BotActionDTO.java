package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.actions.Action;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.services.game.TileService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@Getter
@Setter
public class BotActionDTO {

    @ApiModelProperty(notes = "The first value of a dice", allowableValues = "[1,2,3,4,5,6]")
    private int dice1;

    @ApiModelProperty(notes = "The second value of a dice", allowableValues = "[1,2,3,4,5,6]")
    private int dice2;

    @ApiModelProperty(notes = "The action the bot has taken")
    private String action;

    public BotActionDTO(int dice1, int dice2, ActionType actionType) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.action = actionType.toString();
    }
}
