package ceng453.backend.models.actions;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Tile;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;

public class BuyAction extends BaseAction {

    private Tile tile;

    public BuyAction(Player player, Tile tile) {
        super(player);
        this.tile = tile;
    }

    @Override
    public void execute(TileRepository tileRepository, PlayerRepository playerRepository) {
        if (player.getMoney() < tile.getPrice()) {
            System.out.println("Not enough money");
            throw new IllegalArgumentException("Not enough money to buy this tile");
        }

        tile.setOwner(player);
        tileRepository.save(tile);
        player.setMoney(player.getMoney() - tile.getPrice());
        playerRepository.save(player);

        System.out.println("Tile bought");
        System.out.println("Tile: " + tile.getProperty().getName());
        System.out.println("Price: " + tile.getPrice());
        System.out.println("Owner: " + tile.getOwner().getUser().getUsername());

    }

    @Override
    public ActionType getActionType() {
        return ActionType.BUY;
    }
}
