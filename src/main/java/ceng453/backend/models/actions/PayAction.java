package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;

public class PayAction extends BaseAction {

    private Player owner;
    private int amount;

    public PayAction(Player player, Player owner, int amount) {
        super(player);
        this.owner = owner;
        this.amount = amount;
    }

    @Override
    public void execute() {
        if (player.getMoney() < amount) {
            throw new IllegalArgumentException("Not enough money to pay the owner");
        }
        player.setMoney(player.getMoney() - amount);
        owner.setMoney(owner.getMoney() + amount);
        playerRepository.save(player);
        playerRepository.save(owner);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.NO_ACTION;
    }
}
