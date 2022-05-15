package ceng453.backend.models.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "tiles")
@Entity
@Getter
@Setter
public class Tile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    private Property property;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player owner;
    private Integer location;
    private Integer price;

    public Tile() {
    }

    public Tile(Property property, Game game, Player owner, Integer location, Integer price) {
        this.property = property;
        this.game = game;
        this.owner = owner;
        this.location = location;
        this.price = price;
    }
}