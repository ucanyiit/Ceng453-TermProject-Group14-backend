package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.enums.ActionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TakeActionDTO {
    @ApiModelProperty(value = "Game ID")
    private int gameId;
    @ApiModelProperty(value = "Action Type", allowableValues = "GO_TO_JAIL, BUY, SELL, NO_ACTION, PAY_TAX, CHEAT")
    private ActionType action;
}
