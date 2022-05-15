package ceng453.backend.services.game;

import ceng453.backend.models.enums.GameType;
import ceng453.backend.models.responses.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IGameService {
    /**
     * This method is used to create a new game.
     *
     * @param gameType The type of game to create.
     * @param username The username of the player
     * @return A response entity with the game details
     */
    ResponseEntity<BaseResponse> createGame(GameType gameType, String username);
}
