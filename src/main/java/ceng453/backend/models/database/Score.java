package ceng453.backend.models.database;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name = "scores")
@Entity
@Getter
@Setter
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double score;
    @CreationTimestamp
    private Date timestamp;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "game_id")
    private Game game;

    public Score() {

    }

    public Score(User user, Double score) {
        this.user = user;
        this.score = score;
    }

    public Score(User user, Game game, Double score) {
        this.user = user;
        this.game = game;
        this.score = score;
    }
}