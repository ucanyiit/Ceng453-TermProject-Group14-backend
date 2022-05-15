package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.DTOs.game.TileDTO;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.User;
import ceng453.backend.models.enums.GameType;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.models.responses.game.GameResponse;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.PlayerGameRepository;
import ceng453.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService implements IGameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerGameRepository playerGameRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<BaseResponse> createGame(GameType gameType, String username) {
        User user = userRepository.findByUsername(username);
        Game game = new Game(2, gameType);
        Player player = new Player(user, game, 0);

        userRepository.save(user);
        gameRepository.save(game);
        playerGameRepository.save(player);

        List<TileDTO> tiles = new ArrayList<>();
        
        GameDTO gameDTO = new GameDTO(game.getId(), game.getType(), tiles);
        return new GameResponse(true, "Game is created.", gameDTO).prepareResponse(HttpStatus.OK);
    }
}
