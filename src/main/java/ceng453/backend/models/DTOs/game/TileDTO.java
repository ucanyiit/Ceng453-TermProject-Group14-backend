package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.enums.PropertyType;
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
    private PropertyType propertyType;

    public TileDTO(String name, Integer location, Integer price, PropertyType propertyType) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.propertyType = propertyType;
    }
}
