package ceng453.backend.services.game;

import ceng453.backend.models.database.Game;
import ceng453.backend.models.tiles.TileComposition;

public interface ITileService {
    TileComposition getTileComposition(int gameId, int location);

    void createTiles(Game game);
}
