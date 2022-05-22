package ceng453.backend.models.tiles;

import ceng453.backend.models.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.enums.ActionType;

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
            actions.add(new Action(ActionType.BUY, this.tile.getPrice(), player, 0));
            actions.add(new Action(ActionType.NO_ACTION, 0, null, 0));
        } else {
            actions.add(new Action(ActionType.PAY_TAX, this.tile.getPrice() / 10, player, 0));
        }

        return actions;
    }
}