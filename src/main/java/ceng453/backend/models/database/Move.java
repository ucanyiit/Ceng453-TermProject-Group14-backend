package ceng453.backend.models.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "moves")
@Entity
@Getter
@Setter
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Integer turn;
    private Integer diceRoll1;
    private Integer diceRoll2;
    private Integer currentLocation;
    private Integer previousLocation;

    public Move() {
    }

    public Move(Game game, Player player, Integer turn, Integer diceRoll1, Integer diceRoll2, Integer currentLocation, Integer previousLocation) {
        this.game = game;
        this.player = player;
        this.turn = turn;
        this.diceRoll1 = diceRoll1;
        this.diceRoll2 = diceRoll2;
        this.currentLocation = currentLocation;
        this.previousLocation = previousLocation;
    }
}