package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.DTOs.game.PlayerDTO;
import ceng453.backend.models.DTOs.game.TileDTO;
import ceng453.backend.models.actions.Action;
import ceng453.backend.models.database.*;
import ceng453.backend.models.enums.ActionType;
import ceng453.backend.models.enums.GameType;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.models.responses.game.DiceResponse;
import ceng453.backend.models.responses.game.GameResponse;
import ceng453.backend.models.tiles.GoToJailTile;
import ceng453.backend.models.tiles.TileComposition;
import ceng453.backend.repositories.*;
import ceng453.backend.services.bot.BotService;
import ceng453.backend.services.helper.IHelper;
import ceng453.backend.services.validator.IValidator;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    // Injection
    private final TileService tileService;
    private final BotService botService;
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

    private GameDTO getGameDTO(Game game) {
        List<Player> players = playerRepository.findAllByGame(game);
        List<Tile> tiles = tileRepository.findAllByGame(game);

        return new GameDTO(
                game.getId(),
                game.getType(),
                tiles.stream().map(TileDTO::new).collect(Collectors.toList()),
                players.stream().map(PlayerDTO::new).collect(Collectors.toList()),
                game.getTurnOrder()
        );
    }

    @Override
    public ResponseEntity<BaseResponse> createGame(GameType gameType, String token, Integer playerCount) {
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

        return new GameResponse(true, "Game is created.", getGameDTO(game)).prepareResponse(HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> rollDice(int gameId, String token) {
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
            if (dice.getDice1() == dice.getDice2()) {
                player.setJailDuration(0);
                game.incrementRepeatedDiceCount();
            } else {
                player.setJailDuration(player.getJailDuration() - 1);
                game.setRepeatedDiceCount(0);
            }
            playerRepository.save(player);

            game.advanceTurn();
            gameRepository.save(game);

            TileComposition tileComposition = tileService.getTileComposition(game.getId(), player.getLocation());
            dice.setActions(validator.getValidActions(tileComposition, player));

            return new DiceResponse(
                    true,
                    "Successfully rolled a dice",
                    dice
            ).prepareResponse(HttpStatus.OK);
        }

        TileComposition tileComposition = tileService.getTileComposition(game.getId(), dice.getNewLocation(player.getLocation()));

        if (dice.getDice1() == dice.getDice2()) {
            game.incrementRepeatedDiceCount();
            gameRepository.save(game);
            if (game.getRepeatedDiceCount() == 3) { // if it reaches the count of 3 repeated roll, sent him to the jail
                tileComposition = tileService.getTileComposition(game.getId(), GoToJailTile.LOCATION);
            }
        } else {
            game.setRepeatedDiceCount(0);
            gameRepository.save(game);
        }

        player.setLocation(dice.getNewLocation(player.getLocation()));
        playerRepository.save(player);

        dice.setActions(validator.getValidActions(tileComposition, player));

        return new DiceResponse(
                true,
                "Successfully rolled a dice",
                dice
        ).prepareResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> takeAction(int gameId, ActionType actionType, String token) {
        String username;
        try {
            username = helper.getUsernameFromToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
            return new BaseResponse(false, "Authentication failed.").prepareResponse(HttpStatus.UNAUTHORIZED);
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

        TileComposition tileComposition = tileService.getTileComposition(game.getId(), player.getLocation());
        List<Action> actions = tileComposition.onLand(player);

        for (Action action : actions) {
            if (action.getActionType().equals(actionType)) {
                action.execute(tileRepository, playerRepository);

                return new GameResponse(true, "Action successfully executed", getGameDTO(game)).prepareResponse(HttpStatus.OK);
            }
        }

        return new BaseResponse(false, "Action is not valid").prepareResponse(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<BaseResponse> buyProperty(int gameId, int location, String token) {
        return new BaseResponse(false, "Not implemented yet").prepareResponse(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<BaseResponse> endTurn(int gameId, String token) {
        String username;
        try {
            username = helper.getUsernameFromToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
            return new BaseResponse(false, "Authentication failed.").prepareResponse(HttpStatus.UNAUTHORIZED);
        }

        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null) // game id check
            return new BaseResponse(false, "Game id not found")
                    .prepareResponse(HttpStatus.NOT_FOUND);

        if (!validator.isPlayersTurn(game, username))
            return new BaseResponse(false, "The turn is not the player's")
                    .prepareResponse(HttpStatus.FORBIDDEN);

        // If user has repeated dice rolls, they play again
        if (game.getRepeatedDiceCount() != 0) {
            return new GameResponse(true, "Turn is ended", getGameDTO(game)).prepareResponse(HttpStatus.OK);
        }

        game.advanceTurn();
        gameRepository.save(game);

        // Play others' turns
        if (game.getType().equals(GameType.SINGLEPLAYER)) {
            botService.playTurn(game);
        }

        return new GameResponse(true, "Turn is ended", getGameDTO(game)).prepareResponse(HttpStatus.OK);
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
