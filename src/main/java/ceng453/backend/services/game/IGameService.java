package ceng453.backend.services.game;

import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.enums.GameType;
import ceng453.backend.models.responses.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IGameService {
    /**
     * This method is used to create a new game.
     *
     * @param gameType    The type of game to create.
     * @param token       The user's token used to authenticate.
     * @param playerCount The number of players in the game.
     * @return A response entity with the game details
     */
    ResponseEntity<BaseResponse> createGame(GameType gameType, String token, Integer playerCount);

    ResponseEntity<BaseResponse> rollDice(int gameId, String token);

    ResponseEntity<BaseResponse> takeAction(int gameId, ActionType actionType, String token);

    ResponseEntity<BaseResponse> buyProperty(int gameId, int location, String token);

    ResponseEntity<BaseResponse> endTurn(int gameId, String token);

    ResponseEntity<BaseResponse> resign(int gameId, String token);
}
