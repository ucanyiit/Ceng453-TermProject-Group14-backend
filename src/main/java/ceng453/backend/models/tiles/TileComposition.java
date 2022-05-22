package ceng453.backend.models.tiles;

import ceng453.backend.models.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.database.Tile;
import ceng453.backend.models.enums.ActionType;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class TileComposition {
    protected Tile tile;

    public TileComposition(Property property, Game game, Player owner, Integer location, Integer price) {
        this.tile = new Tile(property, game, owner, location, price);
    }

    public List<Action> onLand(Player player) {
        return Arrays.asList(new Action(ActionType.NO_ACTION, 0, null, 0));
    }
}
