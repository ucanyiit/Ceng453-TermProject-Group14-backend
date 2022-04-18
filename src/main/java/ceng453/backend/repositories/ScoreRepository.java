package ceng453.backend.repositories;

import ceng453.backend.models.Score;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<Score, Integer> {

}