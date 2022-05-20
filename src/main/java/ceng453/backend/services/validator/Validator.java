package ceng453.backend.services.validator;

import ceng453.backend.models.database.Game;
import org.springframework.stereotype.Service;

@Service
public class Validator implements IValidator {

    @Override
    public boolean isPlayersTurn(Game game, String username) {
        return !game // if the turn is not the player's, give an error
                .getPlayersIn()
                .get(game.getTurnOrder())
                .getUser()
                .getUsername()
                .equals(username);
    }
}
