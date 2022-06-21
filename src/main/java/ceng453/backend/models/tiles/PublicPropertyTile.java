package ceng453.backend.models.tiles;

import ceng453.backend.models.actions.Action;
import ceng453.backend.models.actions.BuyAction;
import ceng453.backend.models.actions.NoAction;
import ceng453.backend.models.actions.PayAction;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.database.Tile;

import java.util.ArrayList;
import java.util.List;

public class PublicPropertyTile extends TileComposition {
    private static final Integer PRICE = 250;
    private Property property;

    public PublicPropertyTile(Property property, Game game, Integer location) {
        super(property, game, null, location, PRICE);
        this.property = property;
    }

    private double getRent() {
        List<Tile> tiles = this.tile.getOwner().getProperties();
        for (Tile tile : tiles) {
            Property property = tile.getProperty();
            if (property.getName().startsWith(this.property.getName().substring(0, 3))
                    && !property.getName().equals(this.property.getName())) {
                return this.tile.getPrice() / 5.;
            }
        }

        return this.tile.getPrice() / 10.;
    }

    public List<Action> onLand(Player player) {
        List<Action> actions = new ArrayList();

        if (this.tile.getOwner() == null) {
            if (player.getMoney() >= this.tile.getPrice()) {
                actions.add(new BuyAction(player, this.tile));
            }
            actions.add(new NoAction(player));
        } else if (this.tile.getOwner().getUser().getUsername().equals(player.getUser().getUsername())) {
            actions.add(new NoAction(player));
        } else {
            actions.add(new PayAction(player, this.tile.getOwner(), this.getRent()));
        }

        return actions;
    }
}