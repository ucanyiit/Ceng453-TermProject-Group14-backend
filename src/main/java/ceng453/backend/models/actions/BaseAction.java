package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseAction implements Action {
    protected Player player;

    public BaseAction(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
