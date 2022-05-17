package ceng453.backend.models.responses.game;


import ceng453.backend.models.DTOs.game.DiceDTO;
import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.responses.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiceResponse extends BaseResponse {
    public DiceDTO response;

    /**
     * @param response Scores list.
     */
    public DiceResponse(boolean status, String message, DiceDTO response) {
        super(status, message);
        this.response = response;
    }

}

