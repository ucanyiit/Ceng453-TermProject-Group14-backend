package ceng453.backend.api.game;

import ceng453.backend.models.DTOs.game.BuyPropertyDTO;
import ceng453.backend.models.DTOs.game.StartGameDTO;
import ceng453.backend.models.DTOs.game.TakeActionDTO;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.models.responses.game.DiceResponse;
import ceng453.backend.models.responses.game.EndTurnResponse;
import ceng453.backend.models.responses.game.GameResponse;
import ceng453.backend.services.game.IGameService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/game")
@RequiredArgsConstructor
@RestController
public class GameController {

    // Injection
    private final IGameService gameService;

    /**
     * This method used to create a new game.
     *
     * @param startGameDTO: The StartGameDTO object.
     * @return A response with the game details
     */
    @ApiOperation(
            value = "Create a game",
            notes = "Create a game with the given parameters",
            response = ResponseEntity.class
    )
    @PostMapping(path = "/create-game", produces = "application/json")
    public ResponseEntity<GameResponse> createGame(@RequestBody StartGameDTO startGameDTO, @RequestHeader("Authorization") String token) {
        return gameService.createGame(startGameDTO.getType(), token, 2);
    }

    /**
     * This method used by the players to roll dice during a game
     *
     * @return A json object with the value of dice
     */
    @ApiOperation(
            value = "Roll dice during a game",
            notes = "It is used to provide a dice functionality in a monopoly game",
            response = ResponseEntity.class
    )
    @GetMapping(path = "/roll-dice", produces = "application/json")
    public ResponseEntity<DiceResponse> rollDice(@RequestParam int gameId, @RequestHeader("Authorization") String token) {
        return gameService.rollDice(gameId, token);
    }

    /**
     * This method used to take an action in a game
     *
     * @param takeActionDTO: The TakeActionDTO object.
     * @return A response with the game details
     */
    @ApiOperation(
            value = "Take an action in a game",
            notes = "Take an action in a game with the given action type",
            response = ResponseEntity.class
    )
    @PostMapping(path = "/take-action", produces = "application/json")
    public ResponseEntity<GameResponse> takeAction(@RequestBody TakeActionDTO takeActionDTO, @RequestHeader("Authorization") String token) {
        return gameService.takeAction(takeActionDTO.getGameId(), takeActionDTO.getAction(), token);
    }

    /**
     * This method used to buy a property
     *
     * @return A response with the game details
     */
    @ApiOperation(
            value = "Buy a property",
            response = ResponseEntity.class
    )
    @PostMapping(path = "/buy-property", produces = "application/json")
    public Object buyProperty(@RequestBody BuyPropertyDTO buyPropertyDTO, @RequestHeader("Authorization") String token) {
        return gameService.buyProperty(buyPropertyDTO.getGameId(), buyPropertyDTO.getLocation(), token);
    }

    /**
     * This method used by the players to advance to the next turn
     *
     * @return only the status and message
     */
    @ApiOperation(
            value = "Advances to the next turn",
            notes = "It can be used to end a player's turn",
            response = ResponseEntity.class
    )
    @GetMapping(path = "/next-turn", produces = "application/json")
    public ResponseEntity<EndTurnResponse> nextTurn(@RequestParam int gameId, @RequestHeader("Authorization") String token) {
        return gameService.nextTurn(gameId, token);
    }

    /**
     * This method used by the players to resign from a game
     *
     * @return only the status and message
     */
    @ApiOperation(
            value = "Resign from a game",
            notes = "It is used to resign from a game",
            response = ResponseEntity.class
    )
    @GetMapping(path = "/resign", produces = "application/json")
    public ResponseEntity<BaseResponse> resign(@RequestParam int gameId, @RequestHeader("Authorization") String token) {
        return gameService.resign(gameId, token);
    }
}
