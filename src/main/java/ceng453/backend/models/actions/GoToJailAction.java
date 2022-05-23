package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.tiles.JustVisitingTile;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;

public class GoToJailAction extends BaseAction {

    public GoToJailAction(Player player) {
        super(player);
    }

    @Override
    public void execute(TileRepository tileRepository, PlayerRepository playerRepository) {
        player.setJailDuration(3);
        player.setLocation(JustVisitingTile.LOCATION);
        playerRepository.save(player);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.GO_TO_JAIL;
    }
}
