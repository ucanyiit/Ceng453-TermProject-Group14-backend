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
public class DiceDTO {
    @ApiModelProperty(notes = "Game ID")
    private int gameId;

    @ApiModelProperty(notes = "The first value of a dice", allowableValues = "[1,2,3,4,5,6]")
    private int dice1;

    @ApiModelProperty(notes = "The second value of a dice", allowableValues = "[1,2,3,4,5,6]")
    private int dice2;

    @ApiModelProperty(notes = "The actions user can take")
    private List<ActionType> actions;

    @ApiModelProperty(notes = "Details of the game")
    private GameDTO game;

    public DiceDTO(int gameId) {
        this.gameId = gameId;
    }

    public void rollDice() {
        Random random = new Random();

        // generate a number between 0-5 and add 1 to it.
        this.dice1 = random.nextInt(6) + 1;
        this.dice2 = random.nextInt(6) + 1;
    }

    public int getNewLocation(int location) {
        return (dice1 + dice2 + location) % TileService.TILE_COUNT;
    }
}
