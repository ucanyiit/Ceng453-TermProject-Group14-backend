package ceng453.backend.repositories;

import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Tile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TileRepository extends CrudRepository<Tile, Integer> {
    Tile findByLocationAndGame(Integer location, Game game);

    List<Tile> findAllByGame(Game game);
}