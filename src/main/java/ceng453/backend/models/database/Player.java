package ceng453.backend.models.database;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "players")
@Entity
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    private Integer orderOfPlay;
    @ColumnDefault(value = "1500")
    private Integer money;
    @ColumnDefault(value = "0")
    private Integer jailDuration;
    @ColumnDefault(value = "0")
    private Integer location = 0;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tile> properties = new ArrayList<>();

    public Player() {
    }

    public Player(User user, Game game, Integer orderOfPlay) {
        this.user = user;
        this.game = game;
        this.orderOfPlay = orderOfPlay;
        this.money = 1500;
    }

    public Integer getScore() {
        Integer score = 0;
        for (Tile tile : properties) {
            score += tile.getPrice();
        }
        return score + money;
    }
}