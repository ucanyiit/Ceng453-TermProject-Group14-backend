package ceng453.backend.services.bot;

import ceng453.backend.models.DTOs.game.BotActionDTO;
import ceng453.backend.models.database.Game;
import ceng453.backend.models.database.User;

public interface IBotService {
    BotActionDTO playTurn(Game game);

    User getBotUser(int i);
}
