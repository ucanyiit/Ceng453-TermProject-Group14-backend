package ceng453.backend.models.database;

import ceng453.backend.models.enums.GameType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "games")
@Entity
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ColumnDefault(value = "0")
    private Integer turn = 0;
    @ColumnDefault(value = "2")
    private Integer playerCount = 2;
    @ColumnDefault(value = "0")
    private Integer turnOrder = 0;
    @Enumerated(EnumType.STRING)
    private GameType type;
    @ColumnDefault(value = "0")
    private Integer repeatedDiceCount = 0;

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
}