package ceng453.backend.models;

import javax.persistence.*;

@Table(name = "moves")
@Entity
public class Move {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "action_id", referencedColumnName = "id")
    private Action action;
    private Integer turn_id;
    private Integer diceRoll1;
    private Integer diceRoll2;
    private Integer currentLocation;
    private Integer previousLocation;

    public Move() {
    }

    public Move(Game game, Player player, Integer turn_id, Integer diceRoll1, Integer diceRoll2, Integer currentLocation, Integer previousLocation) {
        this.game = game;
        this.player = player;
        this.turn_id = turn_id;
        this.diceRoll1 = diceRoll1;
        this.diceRoll2 = diceRoll2;
        this.currentLocation = currentLocation;
        this.previousLocation = previousLocation;
    }

    public Integer getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getTurn_id() {
        return turn_id;
    }

    public void setTurn_id(Integer turn_id) {
        this.turn_id = turn_id;
    }

    public Integer getDiceRoll1() {
        return diceRoll1;
    }

    public void setDiceRoll1(Integer diceRoll1) {
        this.diceRoll1 = diceRoll1;
    }

    public Integer getDiceRoll2() {
        return diceRoll2;
    }

    public void setDiceRoll2(Integer diceRoll2) {
        this.diceRoll2 = diceRoll2;
    }

    public Integer getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Integer currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Integer getPreviousLocation() {
        return previousLocation;
    }

    public void setPreviousLocation(Integer previousLocation) {
        this.previousLocation = previousLocation;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}