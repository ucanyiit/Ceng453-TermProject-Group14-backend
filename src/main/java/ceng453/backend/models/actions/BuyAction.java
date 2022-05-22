package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Tile;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.repositories.TileRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BuyAction extends BaseAction {

    private Tile tile;
    @Autowired
    private TileRepository tileRepository;

    public BuyAction(Player player, Tile tile) {
        super(player);
        this.tile = tile;
    }

    @Override
    public void execute() {
        if (player.getMoney() < tile.getPrice()) {
            throw new IllegalArgumentException("Not enough money to buy this tile");
        }
        tile.setOwner(player);
        tileRepository.save(tile);
        player.setMoney(player.getMoney() - tile.getPrice());
        playerRepository.save(player);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.NO_ACTION;
    }
}
