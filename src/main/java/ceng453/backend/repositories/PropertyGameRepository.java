package ceng453.backend.repositories;

import ceng453.backend.models.database.Tile;
import org.springframework.data.repository.CrudRepository;

public interface PropertyGameRepository extends CrudRepository<Tile, Integer> {

}