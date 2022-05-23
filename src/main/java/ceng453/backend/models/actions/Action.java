package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;

public interface Action {
    void execute(TileRepository tileRepository, PlayerRepository playerRepository);

    ActionType getActionType();

    Player getPlayer();
}