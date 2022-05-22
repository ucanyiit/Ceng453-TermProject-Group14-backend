package ceng453.backend.models.tiles;

import ceng453.backend.models.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.enums.TileType;

import java.util.ArrayList;
import java.util.List;

public class JustVisitingTile extends TileComposition {
    public final static Integer LOCATION = 4;

    public JustVisitingTile(Game game) {
        super(new Property("Just Visiting", TileType.VISITING_SPACE), game, null, LOCATION, 0);
    }

    public List<Action> onLand(Player player) {
        List<Action> actions = new ArrayList();
        actions.add(new Action(ActionType.NO_ACTION, 0, null, 0));
        return actions;
    }
}