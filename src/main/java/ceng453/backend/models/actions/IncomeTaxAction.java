package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;

public class IncomeTaxAction extends BaseAction {

    private static double INCOME_TAX_AMOUNT = 50;

    public IncomeTaxAction(Player player) {
        super(player);
    }

    @Override
    public void execute(TileRepository tileRepository, PlayerRepository playerRepository) {
        player.setMoney(player.getMoney() - INCOME_TAX_AMOUNT);
        playerRepository.save(player);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.INCOME_TAX;
    }
}
