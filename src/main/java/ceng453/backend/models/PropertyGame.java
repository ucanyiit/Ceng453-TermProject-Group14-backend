package ceng453.backend.models;

import javax.persistence.*;

@Table(name = "property_games")
@Entity
public class PropertyGame {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    private PropertyType type;
    private Integer price;

    public PropertyGame() {
    }

    public PropertyGame(Property property, Game game, Player owner, Integer location, PropertyType type, Integer price) {
        this.property = property;
        this.game = game;
        this.owner = owner;
        this.location = location;
        this.type = type;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }
    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Game getGame() {
        return game;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Integer getLocation() {
        return location;
    }

    public PropertyType getType() {
        return type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}