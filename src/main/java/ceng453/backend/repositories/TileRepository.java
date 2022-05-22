package ceng453.backend.repositories;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Tile;
import org.springframework.data.repository.CrudRepository;

public interface TileRepository extends CrudRepository<Tile, Integer> {
    Tile findByPlayer(Player player);
}