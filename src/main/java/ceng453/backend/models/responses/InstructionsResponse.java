package ceng453.backend.models.responses;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InstructionsResponse extends BaseResponse {
    public List<String> response;

    /**
     * @param response Scores list.
     */
    public InstructionsResponse(boolean status, String message, List<String> response) {
        super(status, message);
        this.response = response;
    }

}

