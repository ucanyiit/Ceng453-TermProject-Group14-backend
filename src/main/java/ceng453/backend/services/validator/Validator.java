package ceng453.backend.services.validator;

import ceng453.backend.models.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.database.Tile;
import ceng453.backend.models.tiles.TileComposition;
import ceng453.backend.repositories.TileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Validator implements IValidator {

    @Autowired
    TileRepository tileRepository;

    @Override
    public boolean isPlayersTurn(Game game, String username) {
        List<Player> activePlayers = game.getPlayersIn();
        if (activePlayers.size() == 0)
            return false;

        return activePlayers
                .get(game.getTurnOrder())
                .getUser()
                .getUsername()
                .equals(username);
    }

    public List<Action> getValidActions(Player player, Game game) {
        Integer location = player.getLocation();
        Tile tile = tileRepository.findByLocationAndGame(location, game);
        TileComposition tempTileComposition = new TileComposition(tile.getProperty(), tile.getGame(), tile.getOwner(), tile.getLocation(), tile.getPrice());
        return tempTileComposition.onLand(player);
    }
}
