package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;

public interface Action {
    void execute();

    ActionType getActionType();

    Player getPlayer();
}