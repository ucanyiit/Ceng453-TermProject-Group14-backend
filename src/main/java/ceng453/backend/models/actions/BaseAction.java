package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public abstract class BaseAction implements Action {
    protected Player player;

    @Autowired
    protected PlayerRepository playerRepository;

    public BaseAction(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
