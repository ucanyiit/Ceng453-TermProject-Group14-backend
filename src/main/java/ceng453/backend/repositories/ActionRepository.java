package ceng453.backend.repositories;

import ceng453.backend.models.database.Action;
import org.springframework.data.repository.CrudRepository;

public interface ActionRepository extends CrudRepository<Action, Integer> {

}