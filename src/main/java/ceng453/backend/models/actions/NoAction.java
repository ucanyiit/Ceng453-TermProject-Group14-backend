package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;

public class NoAction extends BaseAction {

    public NoAction(Player player) {
        super(player);
    }

    @Override
    public void execute() {
        // Do nothing
    }

    @Override
    public ActionType getActionType() {
        return ActionType.NO_ACTION;
    }
}
