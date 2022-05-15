package ceng453.backend.models.database;

import ceng453.backend.models.enums.ActionType;

import java.util.ArrayList;
import java.util.List;

public class PublicPropertyTile extends Tile {
    public PublicPropertyTile(Property property, Game game, Player owner, Integer location, Integer price) {
        super(property, game, owner, location, price);
    }

    public List<Action> onLand(Player player) {
        List<Action> actions = new ArrayList<Action>();
        if (this.getOwner() == null) {
            actions.add(new Action(ActionType.BUY, this.getPrice(), player, 0));
            actions.add(new Action(ActionType.NO_ACTION, 0, null, 0));
        } else {
            actions.add(new Action(ActionType.PAY_TAX, this.getPrice() / 10, player, 0));
        }

        return actions;
    }
}