package ceng453.backend.models;

import ceng453.backend.models.database.Player;
import ceng453.backend.models.enums.ActionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Action {
    private ActionType type;
    private Integer amount;
    private Player receiverPlayer;
    private Integer profit;

    public Action() {
    }

    public Action(ActionType type, Integer amount, Player receiverPlayer, Integer profit) {
        this.type = type;
        this.amount = amount;
        this.receiverPlayer = receiverPlayer;
        this.profit = profit;
    }
}