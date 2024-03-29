package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;

import java.util.Arrays;

public class PayAction extends BaseAction {

    private final Player owner;
    private final double amount;

    public PayAction(Player player, Player owner, double amount) {
        super(player);
        this.owner = owner;
        this.amount = amount;
    }

    @Override
    public void execute(TileRepository tileRepository, PlayerRepository playerRepository) {
        if (player.getMoney() < amount) {
            throw new IllegalArgumentException("Not enough money to pay the owner");
        }
        player.setMoney(player.getMoney() - amount);
        owner.setMoney(owner.getMoney() + amount);
        playerRepository.saveAll(Arrays.asList(player, owner));
    }

    @Override
    public ActionType getActionType() {
        return ActionType.PAY;
    }
}
