package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;
import ceng453.backend.services.game.TileService;

public class CheatAction extends BaseAction {

    private final static double CHEAT_ACTION_MONEY_DECREASE = 10e7;

    public CheatAction(Player player) {
        super(player);
    }

    @Override
    public void execute(TileRepository tileRepository, PlayerRepository playerRepository) {
        player.setMoney(player.getMoney() - CHEAT_ACTION_MONEY_DECREASE);
        player.setLocation(TileService.INCOME_TAX_LOCATION);
        playerRepository.save(player);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.INCOME_TAX;
    }
}
