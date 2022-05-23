package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;

public class IncomeTaxAction extends BaseAction {

    public IncomeTaxAction(Player player) {
        super(player);
    }

    @Override
    public void execute(TileRepository tileRepository, PlayerRepository playerRepository) {
        player.setMoney(player.getMoney() - 50);
        playerRepository.save(player);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.INCOME_TAX;
    }
}
