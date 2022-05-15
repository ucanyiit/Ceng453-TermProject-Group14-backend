package ceng453.backend.models.responses.game;


import ceng453.backend.models.DTOs.game.GameDTO;
import ceng453.backend.models.responses.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResponse extends BaseResponse {
    public GameDTO response;

    /**
     * @param response Scores list.
     */
    public GameResponse(boolean status, String message, GameDTO response) {
        super(status, message);
        this.response = response;
    }

}

