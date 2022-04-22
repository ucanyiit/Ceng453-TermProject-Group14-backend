package ceng453.backend.models;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private Integer score;
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

    public Score(User user, Integer score) {
        this.user = user;
        this.score = score;
    }

    public Score(User user, Game game, Integer score) {
        this.user = user;
        this.game = game;
        this.score = score;
    }
}