package ceng453.backend.services.game;

import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Property;
import ceng453.backend.models.database.Tile;
import ceng453.backend.models.enums.TileType;
import ceng453.backend.models.tiles.*;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.PropertyRepository;
import ceng453.backend.repositories.TileRepository;
import ceng453.backend.services.helper.IHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TileService {

    public final static Double STARTING_PRIVATE_PROPERTY_PRICE = 100.;
    public final static Double PRIVATE_PROPERTY_PRICE_INCREMENT = 400. / 12;
    public final static Integer TILE_COUNT = 16;
    List<TileComposition> tileCompositions = new ArrayList<>();
    int gameId;
    @Autowired
    private IHelper helper;
    @Autowired
    private TileRepository tileRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    public TileComposition getTileComposition(int gameId, int location) {
        if (tileCompositions.size() == 0 || gameId != this.gameId) {
            createTileCompositions(gameId);
        }
        for (TileComposition tileComposition : tileCompositions) {
            if (tileComposition.getTile().getLocation() == location) {
                return tileComposition;
            }
        }
        throw new RuntimeException("No tile composition found for location " + location);
    }

    private void createTileCompositions(int gameId) {
        this.gameId = gameId;

        Game game = gameRepository.findById(gameId).get();
        List<Tile> tiles = tileRepository.findAllByGame(game);
        tileCompositions = new ArrayList<>();
        for (Tile tile : tiles) {
            TileComposition tileComposition = new TileComposition(tile);
            tileCompositions.add(tileComposition);
        }
    }

    public void createTiles(Game game) {
        this.gameId = game.getId();
        tileCompositions = new ArrayList<>();

        List<Property> properties = createAndGetAllProperties();
        HashSet<Integer> usedLocations = new HashSet<>();

        usedLocations.add(StartingTile.LOCATION);
        usedLocations.add(GoToJailTile.LOCATION);
        usedLocations.add(JustVisitingTile.LOCATION);

        tileCompositions.add(new StartingTile(game));
        tileCompositions.add(new GoToJailTile(game));
        tileCompositions.add(new JustVisitingTile(game));

        int randomLocation = helper.getIntegerNotUsed(usedLocations, TILE_COUNT);

        usedLocations.add(randomLocation);
        tileCompositions.add(new IncomeTaxTile(game, randomLocation));

        int propertyIndex = 0;

        while (tileCompositions.size() < 8) {
            randomLocation = helper.getIntegerNotUsed(usedLocations, TILE_COUNT);
            usedLocations.add(randomLocation);

            Property property = properties.get(propertyIndex++);

            tileCompositions.add(new PublicPropertyTile(property, game, randomLocation));
        }

        double privatePropertyPrice = STARTING_PRIVATE_PROPERTY_PRICE;
        int location = 0;

        while (tileCompositions.size() < TILE_COUNT) {
            Property property = properties.get(propertyIndex++);

            while (usedLocations.contains(location)) location++;
            tileCompositions.add(new PrivatePropertyTile(property, game, location, (int) privatePropertyPrice));
            privatePropertyPrice += PRIVATE_PROPERTY_PRICE_INCREMENT;
            usedLocations.add(location++);
        }

        tileRepository.saveAll(tileCompositions.stream().map(TileComposition::getTile).collect(Collectors.toList()));
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

        List<String> privatePropertyNames = Arrays.asList("p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8");

        for (String name : privatePropertyNames) {
            createAndSaveProperty(name, TileType.PRIVATE_PROPERTY);
        }

        iterable = propertyRepository.findAll();
        properties = new ArrayList<>();
        iterable.forEach(properties::add);

        return properties;
    }

    private void createAndSaveProperty(String name, TileType propertyType) {
        Property property = new Property(name, propertyType);
        propertyRepository.save(property);
    }

}
