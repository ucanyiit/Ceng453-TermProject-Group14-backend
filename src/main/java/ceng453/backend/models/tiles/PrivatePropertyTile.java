package ceng453.backend.models.tiles;

import ceng453.backend.models.actions.Action;
import ceng453.backend.models.actions.BuyAction;
import ceng453.backend.models.actions.NoAction;
import ceng453.backend.models.actions.PayAction;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;

import java.util.ArrayList;
import java.util.List;

public class PrivatePropertyTile extends TileComposition {
    public PrivatePropertyTile(Property property, Game game, Integer location, Integer price) {
        super(property, game, null, location, price);
    }

    public List<Action> onLand(Player player) {
        List<Action> actions = new ArrayList();

        if (this.tile.getOwner() == null) {
            if (player.getMoney() >= this.tile.getPrice()) {
                actions.add(new BuyAction(player, tile));
            }
            actions.add(new NoAction(player));
        } else if (this.tile.getOwner().getUser().getUsername().equals(player.getUser().getUsername())) {
            actions.add(new NoAction(player));
        } else {
            actions.add(new PayAction(player, this.tile.getOwner(), this.tile.getPrice().doubleValue() / 10));
            System.out.println("Payer: " + player.getUser().getUsername() + " Owner: " + this.tile.getOwner().getUser().getUsername());
        }

        return actions;
    }
}