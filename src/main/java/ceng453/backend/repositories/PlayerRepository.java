package ceng453.backend.repositories;

import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Player findByUserAndGame(User user, Game game);

    List<Player> findAllByGame(Game game);

    Player findByGameAndOrderOfPlay(Game game, int orderOfPlay);
}