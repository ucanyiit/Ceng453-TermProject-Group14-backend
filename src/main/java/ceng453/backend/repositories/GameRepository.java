package ceng453.backend.repositories;

import ceng453.backend.models.database.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {

}