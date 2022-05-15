package ceng453.backend.api.game;

import ceng453.backend.models.DTOs.game.StartGameDTO;
import ceng453.backend.services.game.IGameService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(produces = "application/json")
    public Object createGame(@RequestBody StartGameDTO startGameDTO) {

        return gameService.createGame(startGameDTO.getType(), "testUsername");
    }
}
