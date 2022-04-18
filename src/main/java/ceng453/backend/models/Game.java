package ceng453.backend.models;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "games")
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ColumnDefault(value = "0")
    private Integer turn;
    @ColumnDefault(value = "2")
    private Integer playerCount;
    @ColumnDefault(value = "0")
    private Integer turnOrder;
    @Enumerated(EnumType.STRING)
    private GameType type;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Score> scores = new ArrayList<>();
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Player> playersIn = new ArrayList<>();
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Move> movesIn = new ArrayList<>();


    public Game() {

    }

    public Game(Integer playerCount, GameType type) {
        this.playerCount = playerCount;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

    public Integer getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(Integer turnOrder) {
        this.turnOrder = turnOrder;
    }

    public GameType getType() {
        return type;
    }

    public List<Score> getScore() {
        return scores;
    }

    public void setScore(List<Score> scores) {
        this.scores = scores;
    }

    public List<Player> getPlayersIn() {
        return playersIn;
    }

    public void setPlayersIn(List<Player> playersIn) {
        this.playersIn = playersIn;
    }
}