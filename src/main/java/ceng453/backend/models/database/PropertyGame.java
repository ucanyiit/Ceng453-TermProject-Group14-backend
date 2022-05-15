package ceng453.backend.models.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "property_games")
@Entity
@Getter
@Setter
public class PropertyGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player owner;
    private Integer location;
    private Integer price;

    public PropertyGame() {
    }

    public PropertyGame(Property property, Game game, Player owner, Integer location, Integer price) {
        this.property = property;
        this.game = game;
        this.owner = owner;
        this.location = location;
        this.price = price;
    }
}