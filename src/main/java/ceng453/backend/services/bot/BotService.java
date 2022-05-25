package ceng453.backend.services.bot;

import ceng453.backend.models.DTOs.game.BotActionDTO;
import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.actions.Action;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.User;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.tiles.GoToJailTile;
import ceng453.backend.models.tiles.TileComposition;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;
import ceng453.backend.repositories.UserRepository;
import ceng453.backend.services.game.IPlayerService;
import ceng453.backend.services.game.ITileService;
import ceng453.backend.services.validator.IValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BotService implements IBotService {

    private final ITileService tileService;
    private final IPlayerService playerService;

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

    public List<BotActionDTO> playTurn(Game game) {
        DiceDTO dice = new DiceDTO(game.getId());
        dice.rollDice();
        Player bot = game.getPlayersIn().get(1);
        List<BotActionDTO> actionList = new ArrayList<>();

        if (bot.getJailDuration() > 0) {
            playerService.playJailAction(dice, game, bot);
            actionList.add(new BotActionDTO(dice.getDice1(), dice.getDice2(), ActionType.NO_ACTION));
            return actionList;
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
        actionList.add(new BotActionDTO(dice.getDice1(), dice.getDice2(), randomAction.getActionType()));
        randomAction.execute(tileRepository, playerRepository);
        return actionList;
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
