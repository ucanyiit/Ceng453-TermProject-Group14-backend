package ceng453.backend.models.tiles;

import ceng453.backend.models.database.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.enums.ActionType;

import java.util.ArrayList;
import java.util.List;

public class PrivatePropertyTile extends TileComposition {
    public PrivatePropertyTile(Property property, Game game, Integer location, Integer price) {
        super(property, game, null, location, price);
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