package ceng453.backend.models.DTOs.game;

import ceng453.backend.models.database.Player;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PlayerDTO {
    @ApiModelProperty(value = "If the player is a bot.", required = true)
    private boolean isBot;

    @ApiModelProperty(value = "The order of player.", example = "0", required = true)
    private Integer orderOfPlay;

    @ApiModelProperty(value = "The player's current money.", example = "1500", required = true)
    private Integer money;

    @ApiModelProperty(value = "The player's current jail duration.", example = "2", required = true)
    private Integer jailDuration;

    public PlayerDTO(boolean isBot, Integer orderOfPlay, Integer money, Integer jailDuration) {
        this.isBot = isBot;
        this.orderOfPlay = orderOfPlay;
        this.money = money;
        this.jailDuration = jailDuration;
    }

    public PlayerDTO(Player player) {
        this.isBot = player.getUser() == null;
        this.orderOfPlay = player.getOrderOfPlay();
        this.money = player.getMoney();
        this.jailDuration = player.getJailDuration();
    }
}
