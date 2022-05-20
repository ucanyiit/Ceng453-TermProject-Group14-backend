package ceng453.backend.models.responses.game;

import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.DTOs.game.TurnDTO;
import ceng453.backend.models.responses.BaseResponse;
import lombok.Getter;
import lombok.Setter;

public class TurnResponse extends BaseResponse {
    public TurnDTO response;

    public TurnResponse(boolean status, String message, TurnDTO response) {
        super(status, message);
        this.response = response;
    }
}

