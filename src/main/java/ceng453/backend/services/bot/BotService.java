package ceng453.backend.services.bot;

import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.actions.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.User;
import ceng453.backend.models.tiles.GoToJailTile;
import ceng453.backend.models.tiles.TileComposition;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;
import ceng453.backend.repositories.UserRepository;
import ceng453.backend.services.game.TileService;
import ceng453.backend.services.validator.IValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class BotService {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TileRepository tileRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IValidator validator;
    @Autowired
    TileService tileService;

    public void playTurn(Game game) {
        DiceDTO dice = new DiceDTO(game.getId());
        dice.rollDice();
        Player bot = game.getPlayersIn().get(1);

        if (bot.getJailDuration() > 0) {
            if (dice.getDice1() == dice.getDice2()) {
                bot.setJailDuration(0);
                game.incrementRepeatedDiceCount();
            } else {
                bot.setJailDuration(bot.getJailDuration() - 1);
                game.setRepeatedDiceCount(0);
            }
            playerRepository.save(bot);

            game.advanceTurn();
            gameRepository.save(game);

            return;
        }

        TileComposition tileComposition = tileService.getTileComposition(game.getId(), dice.getNewLocation(bot.getLocation()));

        if (dice.getDice1() == dice.getDice2()) {
            game.incrementRepeatedDiceCount();
            gameRepository.save(game);
            if (game.getRepeatedDiceCount() == 3) { // if it reaches the count of 3 repeated roll, sent him to the jail
                tileComposition = tileService.getTileComposition(game.getId(), GoToJailTile.LOCATION);
            }
        } else {
            game.setRepeatedDiceCount(0);
            game.advanceTurn();
            gameRepository.save(game);
        }

        bot.setLocation(dice.getNewLocation(bot.getLocation()));
        playerRepository.save(bot);

        List<Action> actions = tileComposition.onLand(bot);
        Action randomAction = actions.get(new Random().nextInt(actions.size()));
        randomAction.execute(tileRepository, playerRepository);
    }

    public User getBotUser(int i) {
        User botUser = userRepository.findByUsername("Bot " + i);
        if (botUser == null) {
            botUser = new User("Bot " + i, "", UUID.randomUUID().toString(), "");
            userRepository.save(botUser);
        }
        return botUser;
    }

}
