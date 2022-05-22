package ceng453.backend.services.bot;

import ceng453.backend.models.Action;
import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.PlayerGameRepository;
import ceng453.backend.services.validator.IValidator;

import java.util.List;

public class BotService {

    static public List<Action> rollDice(GameRepository gameRepository, PlayerGameRepository playerRepository, Game game, IValidator validator) {
        DiceDTO dice = new DiceDTO(game.getId());
        dice.rollDice();
        Player bot = game.getPlayersIn().get(1);
        if (dice.getDice1() != dice.getDice2()) {
            game.setTurnOrder(0);
            game.setRepeatedDiceCount(0);
            gameRepository.save(game);
        } else {
            game.setRepeatedDiceCount(game.getRepeatedDiceCount() + 1);
            if (game.getRepeatedDiceCount() == 3) { // if it reaches the count of 3 repeated roll, sent him to the jail
                bot.setJailDuration(3);
                playerRepository.save(bot);
            }
        }
        dice.setValidActions(validator.getValidActions(bot, game));
        return null;
    }

    public void takeAction() {

    }
}
