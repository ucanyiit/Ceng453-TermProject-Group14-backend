package ceng453.backend.services.validator;

import ceng453.backend.models.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;

import java.util.List;

public interface IValidator {
    boolean isPlayersTurn(Game game, String username);

    List<Action> getValidActions(Player player, Game game);
}
