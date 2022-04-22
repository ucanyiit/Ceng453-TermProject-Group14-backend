package ceng453.backend.repositories;

import ceng453.backend.models.database.Move;
import org.springframework.data.repository.CrudRepository;

public interface MoveRepository extends CrudRepository<Move, Integer> {

}