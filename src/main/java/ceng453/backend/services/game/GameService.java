package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.BotActionDTO;
import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.DTOs.game.NextTurnDTO;
import ceng453.backend.models.actions.Action;
import ceng453.backend.models.actions.CheatAction;
import ceng453.backend.models.database.*;
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
import java.util.ArrayList;
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


    private Game setupGame(GameType gameType, String username, Integer playerCount) {
        User user = userRepository.findByUsername(username);
        Game game = new Game(playerCount, gameType);
        List<Player> players = new ArrayList<>();

        players.add(new Player(user, game, 0));

        gameRepository.save(game);
        playerRepository.saveAll(players);
        tileService.createTiles(game);
        return game;
    }

    @Override
    public ResponseEntity<GameResponse> createGame(GameType gameType, String token, Integer playerCount) {
        String username;
        try {
            username = helper.getUsernameFromToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
            return new GameResponse(false, "Authentication failed.", null).prepareResponse(HttpStatus.UNAUTHORIZED);
        }

        if (gameType == GameType.MULTIPLAYER) {
            List<Game> allGames = new ArrayList<>();
            gameRepository.findAll().forEach(allGames::add);
            for (Game game : allGames) {
                if (game.getType() != null && game.getType().equals(GameType.MULTIPLAYER)
                        && game.getPlayersIn().size() == 1
                        && !game.getPlayersIn().get(0).getUser().getUsername().equals(username)) {
                    Player player = new Player(userRepository.findByUsername(username), game, 1);
                    playerRepository.save(player);
                    game.addPlayer(player);
                    gameRepository.save(game);
                    return new GameResponse(true, "joined the game.", IGameService.getGameDTO(game, playerRepository, tileRepository))
                            .prepareResponse(HttpStatus.OK);
                }
            }
        }

        Game game = setupGame(gameType, username, playerCount);

        if (gameType == GameType.SINGLEPLAYER) {
            for (int i = 1; i < playerCount; i++) {
                Player bot = new Player(botService.getBotUser(i), game, i);
                game.addPlayer(bot);
                playerRepository.save(bot);
            }
            gameRepository.save(game);
        }

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

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new GameResponse(false, "User does not exist.", null).prepareResponse(HttpStatus.NOT_FOUND);
        }

        Game game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            return new GameResponse(false, "Game does not exist.", null).prepareResponse(HttpStatus.NOT_FOUND);
        }


        Player player = playerRepository.findByUserAndGame(user, game);
        if (player == null) {
            return new GameResponse(false, "You are not a player in this game.", null).prepareResponse(HttpStatus.NOT_FOUND);
        }

        GameDTO gameDTO = IGameService.getGameDTO(game, playerRepository, tileRepository);

        if (game.getPlayersIn().size() == 1) {
            return new GameResponse(false, "Waiting for other player.", gameDTO).prepareResponse(HttpStatus.OK);

        }

        return new GameResponse(true, "Game is retrieved.", gameDTO).prepareResponse(HttpStatus.OK);
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

        if (!player.getDie1().equals(player.getDie2())) {
            game.advanceTurn();
            gameRepository.save(game);
        }

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

        User buyer = userRepository.findByUsername(username);
        if (buyer == null)
            return new GameResponse(false, "User not found", null)
                    .prepareResponse(HttpStatus.NOT_FOUND);

        TileComposition tileComposition = tileService.getTileComposition(gameId, location);
        if (tileComposition == null)
            return new GameResponse(false, "Tile not found", null)
                    .prepareResponse(HttpStatus.NOT_FOUND);

        Tile tile = tileComposition.getTile();
        User seller = tile
                .getOwner()
                .getUser();

        Player playerBuyer = playerRepository.findByUserAndGame(buyer, game);
        Player playerSeller = playerRepository.findByUserAndGame(seller, game);

        if (playerBuyer == null || playerSeller == null)
            return new GameResponse(false, "Players not found", null)
                    .prepareResponse(HttpStatus.NOT_FOUND);

        int propertyCost = tileComposition
                .getTile()
                .getPrice();

        boolean isValidRequest =
                !seller.getUsername().equals(username) // if different player sells the property
                && propertyCost <= playerBuyer.getMoney();

        if (isValidRequest) {
            playerBuyer.setMoney(playerBuyer.getMoney() - propertyCost);
            playerSeller.setMoney(playerSeller.getMoney() + propertyCost);
            tile.setOwner(playerBuyer);

            playerRepository.save(playerBuyer);
            playerRepository.save(playerSeller);
            tileRepository.save(tile);

            return new BaseResponse(true, "The property is bought successfully")
                    .prepareResponse(HttpStatus.OK);
        };



        return new BaseResponse(false, "User not qualified to buy the property")
                .prepareResponse(HttpStatus.BAD_REQUEST);
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

        User user = userRepository.findByUsername(username);

        // If user has the turn, they play again
        if (game.getTurn() == game.getPlayersIn().indexOf(user)) {
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
