package ceng453.backend.models.tiles;

import ceng453.backend.models.actions.Action;
import ceng453.backend.models.actions.NoAction;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.database.Tile;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class TileComposition {
    protected Tile tile;

    public TileComposition(Tile tile) {
        this.tile = tile;
    }

    public TileComposition(Property property, Game game, Player owner, Integer location, Integer price) {
        this.tile = new Tile(property, game, owner, location, price);
    }

    public Tile getTile() {
        return tile;
    }

    public List<Action> onLand(Player player) {
        return Arrays.asList(new NoAction(player));
    }
}
