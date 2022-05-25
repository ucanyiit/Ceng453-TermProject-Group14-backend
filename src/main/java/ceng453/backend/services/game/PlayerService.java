package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements IPlayerService {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;

    public void playJailAction(DiceDTO dice, Game game, Player player) {
        if (dice.getDice1() == dice.getDice2()) {
            player.setJailDuration(0);
            game.incrementRepeatedDiceCount();
        } else {
            player.setJailDuration(player.getJailDuration() - 1);
            game.setRepeatedDiceCount(0);
            game.advanceTurn();
        }

        playerRepository.save(player);
        gameRepository.save(game);
    }
}
