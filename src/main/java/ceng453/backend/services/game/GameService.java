package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.BotActionDTO;
import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.DTOs.game.NextTurnDTO;
import ceng453.backend.models.actions.Action;
import ceng453.backend.models.actions.CheatAction;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Score;
import ceng453.backend.models.database.User;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.enums.GameType;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.models.responses.game.DiceResponse;
import ceng453.backend.models.responses.game.EndTurnResponse;
import ceng453.backend.models.responses.game.GameResponse;
import ceng453.backend.models.tiles.TileComposition;
import ceng453.backend.repositories.*;
import ceng453.backend.services.bot.IBotService;
import ceng453.backend.services.helper.IHelper;
import ceng453.backend.services.validator.IValidator;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    private final ITileService tileService;
    private final IBotService botService;
    private final IPlayerService playerService;

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TileRepository tileRepository;
    @Autowired
    private IHelper helper;
    @Autowired
    private IValidator validator;

    @Override
    public ResponseEntity<GameResponse> createGame(GameType gameType, String token, Integer playerCount) {
        String username;
        try {
            username = helper.getUsernameFromToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
            return new GameResponse(false, "Authentication failed.", null).prepareResponse(HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findByUsername(username);
        Game game = new Game(playerCount, gameType);
        List<Player> players = new ArrayList<>();

        players.add(new Player(user, game, 0));

        if (gameType == GameType.SINGLEPLAYER) {
            for (int i = 1; i < playerCount; i++) {
                players.add(new Player(botService.getBotUser(i), game, i));
            }
        }

        gameRepository.save(game);
        playerRepository.saveAll(players);
        tileService.createTiles(game);

        return new GameResponse(true, "Game is created.", IGameService.getGameDTO(game, playerRepository, tileRepository))
                .prepareResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GameResponse> getCurrentStateGame(int gameId, String token) {
        String username;
        try {
            username = helper.getUsernameFromToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
            return new GameResponse(false, "Authentication failed.", null).prepareResponse(HttpStatus.UNAUTHORIZED);
        }
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            return new GameResponse(false, "Game does not exist.", null).prepareResponse(HttpStatus.NOT_FOUND);
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new GameResponse(false, "User does not exist.", null).prepareResponse(HttpStatus.NOT_FOUND);
        }

        Player player = playerRepository.findByUserAndGame(user, game);
        if (player == null) {
            return new GameResponse(false, "You are not a player in this game.", null).prepareResponse(HttpStatus.NOT_FOUND);
        }

        return new GameResponse(true, "Game is retrieved.", IGameService.getGameDTO(game, playerRepository, tileRepository))
                .prepareResponse(HttpStatus.OK);

    }

    public ResponseEntity<DiceResponse> rollDice(int gameId, String token) {
        String username;
        try {
            username = helper.getUsernameFromToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
            return new DiceResponse(false, "Authentication failed.", null).prepareResponse(HttpStatus.UNAUTHORIZED);
        }

        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null) // game id check
            return new DiceResponse(false, "Game id not found", null)
                    .prepareResponse(HttpStatus.NOT_FOUND);

        if (!validator.isPlayersTurn(game, username))
            return new DiceResponse(false, "The turn is not the player's", null)
                    .prepareResponse(HttpStatus.FORBIDDEN);


        User user = userRepository.findByUsername(username);
        Player player = playerRepository.findByUserAndGame(user, game);

        DiceDTO dice = new DiceDTO(gameId);
        dice.rollDice();

        if (player.getJailDuration() > 0) {
            playerService.playJailAction(dice, game, player);

            TileComposition tileComposition = tileService.getTileComposition(game.getId(), player.getLocation());
            dice.setActions(validator.getValidActions(tileComposition, player));
            dice.setGame(IGameService.getGameDTO(game, playerRepository, tileRepository));

            return new DiceResponse(
                    true,
                    "Successfully rolled a dice",
                    dice
            ).prepareResponse(HttpStatus.OK);
        }

        dice = playerService.playDiceAndConstructDiceDTO(dice, player, game);

        return new DiceResponse(
                true,
                "Successfully rolled a dice",
                dice
        ).prepareResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GameResponse> takeAction(int gameId, ActionType actionType, String token) {
        String username;
        try {
            username = helper.getUsernameFromToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
            return new GameResponse(false, "Authentication failed.", null).prepareResponse(HttpStatus.UNAUTHORIZED);
        }

        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null) // game id check
            return new GameResponse(false, "Game id not found", null)
                    .prepareResponse(HttpStatus.NOT_FOUND);

        if (!validator.isPlayersTurn(game, username))
            return new GameResponse(false, "The turn is not the player's", null)
                    .prepareResponse(HttpStatus.FORBIDDEN);

        User user = userRepository.findByUsername(username);
        Player player = playerRepository.findByUserAndGame(user, game);

        TileComposition tileComposition = tileService.getTileComposition(game.getId(), player.getLocation());
        List<Action> actions = tileComposition.onLand(player);

        for (Action action : actions) {
            if (action.getActionType().equals(actionType)) {
                action.execute(tileRepository, playerRepository);

                return new GameResponse(
                        true,
                        "Action successfully executed",
                        IGameService.getGameDTO(game, playerRepository, tileRepository)).prepareResponse(HttpStatus.OK);
            }
        }

        if (actionType.equals(ActionType.CHEAT)) {
            Action action = new CheatAction(player);
            action.execute(tileRepository, playerRepository);

            return new GameResponse(
                    true,
                    "Action successfully executed",
                    IGameService.getGameDTO(game, playerRepository, tileRepository)).prepareResponse(HttpStatus.OK);
        }

        return new BaseResponse(false, "Action is not valid").prepareResponse(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<BaseResponse> buyProperty(int gameId, int location, String token) {
        return new BaseResponse(false, "Not implemented yet").prepareResponse(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<EndTurnResponse> nextTurn(int gameId, String token) {
        String username;
        try {
            username = helper.getUsernameFromToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
            return new EndTurnResponse(false, "Authentication failed.", null).prepareResponse(HttpStatus.UNAUTHORIZED);
        }

        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null) // game id check
            return new EndTurnResponse(false, "Game id not found", null)
                    .prepareResponse(HttpStatus.NOT_FOUND);

        if (!validator.isPlayersTurn(game, username))
            return new EndTurnResponse(false, "The turn is not the player's", null)
                    .prepareResponse(HttpStatus.FORBIDDEN);

        NextTurnDTO response = new NextTurnDTO(gameId, null, null);

        // If user has the turn, they play again
        if (game.getTurn() == 0) {
            response.setGame(IGameService.getGameDTO(game, playerRepository, tileRepository));
            return new EndTurnResponse(true, "Turn is ended", response).prepareResponse(HttpStatus.OK);
        }

        // Play others' turns
        if (game.getType().equals(GameType.SINGLEPLAYER)) {
            BotActionDTO botAction = botService.playTurn(game);
            response.setBotAction(botAction);
            response.setGame(IGameService.getGameDTO(game, playerRepository, tileRepository));
            return new EndTurnResponse(true, "Turn is ended", response)
                    .prepareResponse(HttpStatus.OK);
        }

        response.setGame(IGameService.getGameDTO(game, playerRepository, tileRepository));

        return new EndTurnResponse(true, "Turn is ended", response)
                .prepareResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> resign(int gameId, String token) {
        String username;
        try {
            username = helper.getUsernameFromToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
            return new BaseResponse(false, "Authentication failed.").prepareResponse(HttpStatus.UNAUTHORIZED);
        }

        Game game = gameRepository.findById(gameId).orElse(null);
        User user = userRepository.findByUsername(username);
        Player player = playerRepository.findByUserAndGame(user, game);

        if (game == null || user == null || player == null) { // game id check
            return new BaseResponse(false, "Game id, player or user not found").prepareResponse(HttpStatus.NOT_FOUND);
        }

        game.setEndDate(LocalDateTime.now());
        gameRepository.save(game);

        Score score = new Score(user, game, player.getScore());
        scoreRepository.save(score);

        return new BaseResponse(true, "Successfully resigned").prepareResponse(HttpStatus.OK);
    }
}
