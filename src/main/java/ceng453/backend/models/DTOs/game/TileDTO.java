package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.database.Tile;
import ceng453.backend.models.enums.TileType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class TileDTO {
    @ApiModelProperty(value = "The name of the property.", example = "", required = true)
    private String name;

    @ApiModelProperty(value = "The location of property.", example = "3", required = true)
    private Integer location;

    @ApiModelProperty(value = "The price of property.", example = "1000")
    private Integer price;

    @ApiModelProperty(value = "The type of the property.", allowableValues = "JAIL, INCOME_TAX, PUBLIC_PROPERTY, PRIVATE_PROPERTY, VISITING_SPACE, STARTING_POINT", required = true)
    private TileType propertyType;

    @ApiModelProperty(value = "The name of the owner of the property.", example = "bahadir")
    private String owner;

    public TileDTO(String name, Integer location, Integer price, TileType propertyType, String owner) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.propertyType = propertyType;
        this.owner = owner;
    }

    public TileDTO(Tile tile) {
        this.name = tile.getProperty().getName();
        this.location = tile.getLocation();
        this.price = tile.getPrice();
        this.propertyType = tile.getProperty().getType();
        this.owner = tile.getOwner() == null ? "" : tile.getOwner().getUser().getUsername();
    }
}
