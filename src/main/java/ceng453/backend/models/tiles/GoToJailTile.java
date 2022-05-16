package ceng453.backend.models.tiles;

import ceng453.backend.models.database.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class GoToJailTile extends TileComposition {
    public final static Integer LOCATION = 12;

    public GoToJailTile(Game game) {
        super(new Property("Go To Jail", TileType.JAIL), game, null, LOCATION, 0);
    }

    public List<Action> onLand(Player player) {
        List<Action> actions = new ArrayList();
        actions.add(new Action(ActionType.GO_TO_JAIL, 0, player, 0));

        return actions;
    }
}