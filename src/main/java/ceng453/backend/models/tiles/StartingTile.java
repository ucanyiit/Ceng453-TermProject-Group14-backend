package ceng453.backend.models.tiles;

import ceng453.backend.models.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class StartingTile extends TileComposition {
    public final static Integer LOCATION = 0;

    public StartingTile(Game game) {
        super(new Property("Start", TileType.STARTING_POINT), game, null, LOCATION, 0);
    }

    public List<Action> onLand(Player player) {
        List<Action> actions = new ArrayList();
        actions.add(new Action(ActionType.NO_ACTION, 0, player, 0));

        return actions;
    }
}