package ceng453.backend.repositories;

import ceng453.backend.models.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerGameRepository extends CrudRepository<Player, Integer> {

}