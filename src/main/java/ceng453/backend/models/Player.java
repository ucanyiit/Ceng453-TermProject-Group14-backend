package ceng453.backend.models;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "players")
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    private Integer orderOfPlay;
    @ColumnDefault(value = "1500")
    private Integer money;
    @ColumnDefault(value = "0")
    private Integer jailDuration;
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Move> moves = new ArrayList<>();
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PropertyGame> properties = new ArrayList<>();
    @OneToMany(mappedBy = "receiverPlayer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Action> receivedActions = new ArrayList<>();

    public Player() {
    }

    public Player(User user, Game game, Integer orderOfPlay) {
        this.user = user;
        this.game = game;
        this.orderOfPlay = orderOfPlay;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Game getGame() {
        return game;
    }

    public Integer getOrderOfPlay() {
        return orderOfPlay;
    }

    public void setOrderOfPlay(Integer order) {
        this.orderOfPlay = order;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getJailDuration() {
        return jailDuration;
    }

    public void setJailDuration(Integer jailDuration) {
        this.jailDuration = jailDuration;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public List<PropertyGame> getProperties() {
        return properties;
    }

    public List<Action> getReceivedActions() {
        return receivedActions;
    }
}