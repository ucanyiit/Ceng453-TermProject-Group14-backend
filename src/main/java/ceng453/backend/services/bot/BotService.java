package ceng453.backend.services.bot;

import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.actions.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.tiles.TileComposition;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.services.game.TileService;
import ceng453.backend.services.validator.IValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotService {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    IValidator validator;
    TileService tileService;

    public List<Action> rollDice(Game game) {
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
        TileComposition tileComposition = tileService.getTileComposition(game.getId(), dice.getNewLocation(bot.getLocation()));
        dice.setActions(validator.getValidActions(tileComposition));
        return null;
    }

    public void takeAction() {

    }
}
