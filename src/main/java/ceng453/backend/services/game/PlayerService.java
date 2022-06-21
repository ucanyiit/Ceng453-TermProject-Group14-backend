package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;
import ceng453.backend.models.tiles.GoToJailTile;
import ceng453.backend.models.tiles.TileComposition;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.PlayerRepository;
import ceng453.backend.repositories.TileRepository;
import ceng453.backend.services.validator.IValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService implements IPlayerService {

    private final ITileService tileService;
    private final IValidator validator;

    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TileRepository tileRepository;

    public void playJailAction(DiceDTO dice, Game game, Player player) {
        if (dice.getDice1() == dice.getDice2()) {
            player.setJailDuration(0);
            game.incrementRepeatedDiceCount();
        } else {
            player.setJailDuration(player.getJailDuration() - 1);
            game.setRepeatedDiceCount(0);
        }

        playerRepository.save(player);
        gameRepository.save(game);
    }

    public DiceDTO playDiceAndConstructDiceDTO(DiceDTO dice, Player player, Game game) {
        player.setDice(dice.getDice1(), dice.getDice2());
        int newLocation = dice.getNewLocation(player.getLocation());
        TileComposition tileComposition = tileService.getTileComposition(game.getId(), newLocation);

        if (newLocation < player.getLocation()) {
            player.setMoney(player.getMoney() + 100);
        }

        if (dice.getDice1() == dice.getDice2()) {
            game.incrementRepeatedDiceCount();
            gameRepository.save(game);
            if (game.getRepeatedDiceCount() == 3) { // if it reaches the count of 3 repeated roll, send them to the jail
                tileComposition = tileService.getTileComposition(game.getId(), GoToJailTile.LOCATION);
            }
        } else {
            game.setRepeatedDiceCount(0);
            gameRepository.save(game);
        }

        player.setLocation(newLocation);
        playerRepository.save(player);

        dice.setActions(validator.getValidActions(tileComposition, player));
        dice.setGame(IGameService.getGameDTO(game, playerRepository, tileRepository));

        return dice;
    }
}
