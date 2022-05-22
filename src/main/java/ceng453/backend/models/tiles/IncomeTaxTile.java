package ceng453.backend.models.tiles;

import ceng453.backend.models.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxTile extends TileComposition {
    public IncomeTaxTile(Game game, Integer location) {
        super(new Property("Income Tax", TileType.INCOME_TAX), game, null, location, 0);
    }

    public List<Action> onLand(Player player) {
        List<Action> actions = new ArrayList();
        actions.add(new Action(ActionType.PAY_TAX, 50, player, 0));

        return actions;
    }
}