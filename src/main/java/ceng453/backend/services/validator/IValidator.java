package ceng453.backend.services.validator;

import ceng453.backend.models.database.Game;

public interface IValidator {
    boolean isPlayersTurn(Game game, String username);
}
