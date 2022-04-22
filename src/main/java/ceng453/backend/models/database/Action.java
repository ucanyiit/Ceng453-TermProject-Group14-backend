package ceng453.backend.models.database;

import ceng453.backend.models.enums.ActionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "actions")
@Entity
@Getter
@Setter
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private ActionType type;
    private Integer amount;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_player_id", nullable = false)
    private Player receiverPlayer;
    private Integer profit;
    @OneToOne(mappedBy = "action")
    private Move move;

    public Action() {
    }

    public Action(ActionType type, Integer amount, Player receiverPlayer, Integer profit) {
        this.type = type;
        this.amount = amount;
        this.receiverPlayer = receiverPlayer;
        this.profit = profit;
    }
}