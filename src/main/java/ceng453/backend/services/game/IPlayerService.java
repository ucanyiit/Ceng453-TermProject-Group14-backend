package ceng453.backend.services.game;

import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.Player;

public interface IPlayerService {
    void playJailAction(DiceDTO dice, Game game, Player player);

    DiceDTO playDiceAndConstructDiceDTO(DiceDTO dice, Player player, Game game);
}
