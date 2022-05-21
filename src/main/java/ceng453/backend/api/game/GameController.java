package ceng453.backend.api.game;

import ceng453.backend.models.DTOs.game.StartGameDTO;
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
    public Object createGame(@RequestBody StartGameDTO startGameDTO, @RequestHeader("Authorization") String token) {
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
    public Object rollDice(@RequestParam int gameId, @RequestHeader("Authorization") String token) {
        return gameService.rollDice(gameId, token);
    }


    /**
     * This method used by the players to end the current turn
     *
     * @return only the status and message
     */
    @ApiOperation(
            value = "End a turn",
            notes = "It can be used to end a player's turn",
            response = ResponseEntity.class
    )
    @GetMapping(path = "/end-turn", produces = "application/json")
    public Object endTurn(@RequestParam int gameId, @RequestHeader("Authorization") String token) {
        return gameService.endTurn(gameId, token);
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
    public Object resign(@RequestParam int gameId, @RequestHeader("Authorization") String token) {
        return gameService.resign(gameId, token);
    }
}
