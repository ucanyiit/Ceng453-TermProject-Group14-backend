package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.DTOs.game.TileDTO;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.database.User;
import ceng453.backend.models.enums.GameType;
import ceng453.backend.models.enums.TileType;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.models.responses.game.GameResponse;
import ceng453.backend.models.tiles.*;
import ceng453.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService implements IGameService {

    private final static Double STARTING_PRIVATE_PROPERTY_PRICE = 100.;
    private final static Double PRIVATE_PROPERTY_PRICE_INCREMENT = 400. / 12;

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerGameRepository playerGameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private TileRepository tileRepository;

    @Override
    public ResponseEntity<BaseResponse> createGame(GameType gameType, String username) {
        User user = userRepository.findByUsername(username);
        Game game = new Game(2, gameType);
        Player player = new Player(user, game, 0);

        gameRepository.save(game);
        playerGameRepository.save(player);

        GameDTO gameDTO = new GameDTO(game.getId(), game.getType(), createAndGetTiles(game));
        return new GameResponse(true, "Game is created.", gameDTO).prepareResponse(HttpStatus.OK);
    }

    private Integer getIntegerNotUsed(HashSet<Integer> usedNumbers, Integer limit) {
        while (true) {
            int randomNumber = (int) (Math.random() * limit);
            if (!usedNumbers.contains(randomNumber)) {
                return randomNumber;
            }
        }
    }

    public List<TileDTO> createAndGetTiles(Game game) {
        List<Property> properties = createAndGetAllProperties();
        List<TileComposition> tileCompositions = new ArrayList<>();
        HashSet<Integer> usedLocations = new HashSet<>();

        usedLocations.add(StartingTile.LOCATION);
        usedLocations.add(GoToJailTile.LOCATION);
        usedLocations.add(JustVisitingTile.LOCATION);

        tileCompositions.add(new StartingTile(game));
        tileCompositions.add(new GoToJailTile(game));
        tileCompositions.add(new JustVisitingTile(game));

        int randomLocation = getIntegerNotUsed(usedLocations, 20);

        usedLocations.add(randomLocation);
        tileCompositions.add(new IncomeTaxTile(game, randomLocation));

        int propertyIndex = 0;

        while (tileCompositions.size() < 8) {
            randomLocation = getIntegerNotUsed(usedLocations, 20);
            usedLocations.add(randomLocation);

            Property property = properties.get(propertyIndex++);

            tileCompositions.add(new PublicPropertyTile(property, game, randomLocation));
        }

        double privatePropertyPrice = STARTING_PRIVATE_PROPERTY_PRICE;
        int location = 0;

        while (tileCompositions.size() < 20) {
            Property property = properties.get(propertyIndex++);

            while (usedLocations.contains(location)) location++;
            tileCompositions.add(new PrivatePropertyTile(property, game, location, (int) privatePropertyPrice));
            privatePropertyPrice += PRIVATE_PROPERTY_PRICE_INCREMENT;
            usedLocations.add(location++);
        }

        for (TileComposition tileComposition : tileCompositions) {
            tileRepository.save(tileComposition.getTile());
        }

        return tileCompositions
                .stream()
                .map(tileComposition -> new TileDTO(tileComposition.getTile()))
                .collect(Collectors.toList());
    }

    private void createAndSaveProperty(String name, TileType propertyType) {
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

        List<String> publicPropertyNames = Arrays.asList("Railroad 1", "Railroad 2", "Ferry 1", "Ferry 2");

        for (String name : publicPropertyNames) {
            createAndSaveProperty(name, TileType.PUBLIC_PROPERTY);
        }

        List<String> privatePropertyNames = Arrays.asList("p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9", "p10", "p11", "p12");

        for (String name : privatePropertyNames) {
            createAndSaveProperty(name, TileType.PRIVATE_PROPERTY);
        }

        iterable = propertyRepository.findAll();
        properties = new ArrayList<>();
        iterable.forEach(properties::add);

        return properties;
    }
}
