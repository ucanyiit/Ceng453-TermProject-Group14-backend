package ceng453.backend.models.tiles;

import ceng453.backend.models.actions.Action;
import ceng453.backend.models.actions.IncomeTaxAction;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxTile extends TileComposition {
    public IncomeTaxTile(Game game, Integer location) {
        super(new Property("Income Tax", TileType.INCOME_TAX), game, null, location, 0);
    }

    public List<Action> onLand(Player player) {
        List<Action> actions = new ArrayList();
        actions.add(new IncomeTaxAction(player));

        return actions;
    }
}