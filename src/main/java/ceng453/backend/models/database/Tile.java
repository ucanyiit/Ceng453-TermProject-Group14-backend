package ceng453.backend.models.database;

import ceng453.backend.models.enums.ActionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Table(name = "tiles")
@Entity
@Getter
@Setter
public class Tile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
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

    public List<Action> onLand(Player player) {
        return Arrays.asList(new Action(ActionType.NO_ACTION, 0, null, 0));
    }
}