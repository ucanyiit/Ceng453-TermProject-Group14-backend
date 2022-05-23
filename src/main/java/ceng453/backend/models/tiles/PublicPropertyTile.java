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

public class PublicPropertyTile extends TileComposition {
    private static final Integer PRICE = 250;

    public PublicPropertyTile(Property property, Game game, Integer location) {
        super(property, game, null, location, PRICE);
    }

    public List<Action> onLand(Player player) {
        List<Action> actions = new ArrayList();
        if (this.tile.getOwner() == null) {
            if (player.getMoney() >= this.tile.getPrice()) {
                actions.add(new BuyAction(player, this.tile));
            }
            actions.add(new NoAction(player));
        } else {
            actions.add(new PayAction(player, this.tile.getOwner(), this.tile.getPrice() / 10));
        }

        return actions;
    }
}