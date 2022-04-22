package ceng453.backend.repositories;

import ceng453.backend.models.database.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, Integer> {

}