package ceng453.backend.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private String passwordReminder;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Score> scores = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Player> gamesIn = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String email, String passwordReminder) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.passwordReminder = passwordReminder;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordReminder() {
        return passwordReminder;
    }

    public void setPasswordReminder(String passwordReminder) {
        this.passwordReminder = passwordReminder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Score> getScore() {
        return scores;
    }

    public void setScore(List<Score> score) {
        this.scores = score;
    }

    public List<Player> getGamesIn() {
        return gamesIn;
    }

    public void setGamesIn(List<Player> gamesIn) {
        this.gamesIn = gamesIn;
    }
}