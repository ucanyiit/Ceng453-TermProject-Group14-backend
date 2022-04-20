package ceng453.backend.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name = "scores")
@Entity
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

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}