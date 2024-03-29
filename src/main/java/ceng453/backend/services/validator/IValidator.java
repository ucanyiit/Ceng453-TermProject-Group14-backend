package ceng453.backend.services.validator;

import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.tiles.TileComposition;

import java.util.List;

public interface IValidator {
    boolean isPlayersTurn(Game game, String username);

    List<ActionType> getValidActions(TileComposition tileComposition, Player player);
}
