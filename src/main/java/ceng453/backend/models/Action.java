package ceng453.backend.models;

import javax.persistence.*;

@Table(name = "actions")
@Entity
public class Action {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    public Integer getId() {
        return id;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Player getReceiverPlayer() {
        return receiverPlayer;
    }

    public void setReceiverPlayer(Player receiverPlayer) {
        this.receiverPlayer = receiverPlayer;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public Move getMove() {
        return move;
    }
}