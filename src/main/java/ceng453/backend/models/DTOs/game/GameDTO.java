package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.enums.GameType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class GameDTO {
    @ApiModelProperty(value = "The tiles in the game.")
    List<TileDTO> tiles;
    @ApiModelProperty(value = "The players in the game.")
    List<PlayerDTO> players;
    @ApiModelProperty(notes = "Game ID")
    private int gameId;
    @ApiModelProperty(value = "The type of the game.", allowableValues = "SINGLEPLAYER, MULTIPLAYER")
    private GameType type;
    @ApiModelProperty(notes = "The current turn of the game.")
    private int turn;

    public GameDTO(int gameId, GameType type, List<TileDTO> tiles, List<PlayerDTO> players, int turn) {
        this.gameId = gameId;
        this.type = type;
        this.tiles = tiles;
        this.players = players;
        this.turn = turn;
    }
}
