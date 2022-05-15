package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.DTOs.game.TileDTO;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.database.User;
import ceng453.backend.models.enums.GameType;
import ceng453.backend.models.enums.PropertyType;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.models.responses.game.GameResponse;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.PlayerGameRepository;
import ceng453.backend.repositories.PropertyRepository;
import ceng453.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GameService implements IGameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerGameRepository playerGameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;

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

    public List<TileDTO> createAndGetTiles(Game game) {
        List<Property> properties = createAndGetAllProperties();
        List<TileDTO> tiles = new ArrayList<>();

        // TODO: Create tiles
        
        return tiles;
    }

    private void createAndSaveProperty(String name, PropertyType propertyType) {
        Property property = new Property(name, propertyType);
        propertyRepository.save(property);
    }

    private List<Property> createAndGetAllProperties() {
        Iterable<Property> iterable = propertyRepository.findAll();
        List<Property> properties = new ArrayList<>();
        iterable.forEach(properties::add);

        if (!properties.isEmpty()) {
            return properties;
        }

        createAndSaveProperty("Start", PropertyType.STARTING_POINT);
        createAndSaveProperty("Jail", PropertyType.JAIL);
        createAndSaveProperty("Income Tax", PropertyType.INCOME_TAX);
        createAndSaveProperty("Just Visiting", PropertyType.VISITING_SPACE);

        List<String> privatePropertyNames = Arrays.asList("p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9", "p10", "p11", "p12");

        for (String name : privatePropertyNames) {
            createAndSaveProperty(name, PropertyType.PRIVATE_PROPERTY);
        }

        List<String> publicPropertyNames = Arrays.asList("Railroad 1", "Railroad 2", "Ferry 1", "Ferry 2");

        for (String name : publicPropertyNames) {
            createAndSaveProperty(name, PropertyType.PUBLIC_PROPERTY);
        }

        iterable = propertyRepository.findAll();
        properties = new ArrayList<>();
        iterable.forEach(properties::add);

        return properties;
    }
}
