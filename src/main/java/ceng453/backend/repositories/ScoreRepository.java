package ceng453.backend.repositories;

import ceng453.backend.models.Score;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, Integer> {
    List<Score> findAllByTimestampBetween(
            Date leaderboardTimeStart,
            Date leaderboardTimeEnd);

}