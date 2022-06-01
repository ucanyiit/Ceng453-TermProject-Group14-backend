package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.DTOs.game.PlayerDTO;
import ceng453.backend.models.DTOs.game.TileDTO;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Tile;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.enums.GameType;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.models.responses.game.DiceResponse;
import ceng453.backend.models.responses.game.EndTurnResponse;
import ceng453.backend.models.responses.game.GameResponse;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface IGameService {
    static GameDTO getGameDTO(Game game, PlayerRepository playerRepository, TileRepository tileRepository) {
        List<Player> players = playerRepository.findAllByGame(game);
        List<Tile> tiles = tileRepository.findAllByGame(game);

        return new GameDTO(
                game.getId(),
                game.getType(),
                tiles.stream().map(TileDTO::new).collect(Collectors.toList()),
                players.stream().map(PlayerDTO::new).collect(Collectors.toList()),
                game.getTurn()
        );
    }

    /**
     * This method is used to create a new game.
     *
     * @param gameType    The type of game to create.
     * @param token       The user's token used to authenticate.
     * @param playerCount The number of players in the game.
     * @return A response entity with the game details
     */
    ResponseEntity<GameResponse> createGame(GameType gameType, String token, Integer playerCount);

    ResponseEntity<DiceResponse> rollDice(int gameId, String token);

    ResponseEntity<GameResponse> takeAction(int gameId, ActionType actionType, String token);

    ResponseEntity<BaseResponse> buyProperty(int gameId, int location, String token);

    ResponseEntity<EndTurnResponse> nextTurn(int gameId, String token);

    ResponseEntity<BaseResponse> resign(int gameId, String token);
}
