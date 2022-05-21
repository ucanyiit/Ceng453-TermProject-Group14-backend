package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.database.Player;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PlayerDTO {
    @ApiModelProperty(value = "If the player is a bot.", required = true)
    private boolean isBot;

    @ApiModelProperty(value = "The username of the player.", example = "ucanyiit", required = true)
    private String username;

    @ApiModelProperty(value = "The order of player.", example = "0", required = true)
    private Integer orderOfPlay;

    @ApiModelProperty(value = "The player's current money.", example = "1500", required = true)
    private Integer money;

    @ApiModelProperty(value = "The player's current jail duration.", example = "2", required = true)
    private Integer jailDuration;

    @ApiModelProperty(value = "The player's current location.", example = "4", required = true)
    private Integer location;


    public PlayerDTO(boolean isBot, String username, Integer orderOfPlay, Integer money, Integer jailDuration, Integer location) {
        this.isBot = isBot;
        this.username = username;
        this.orderOfPlay = orderOfPlay;
        this.money = money;
        this.jailDuration = jailDuration;
        this.location = location;
    }

    public PlayerDTO(Player player, Integer location) {
        this.isBot = player.getUser() == null;
        this.username = this.isBot ? "BOT" : player.getUser().getUsername();
        this.orderOfPlay = player.getOrderOfPlay();
        this.money = player.getMoney();
        this.jailDuration = player.getJailDuration();
        this.location = location;
    }
}
