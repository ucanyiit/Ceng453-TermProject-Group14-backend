package ceng453.backend.models.authDTOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UsernameDTO {
    @ApiModelProperty(value = "The unique username", example = "yigitucan")
    private String username;

    public UsernameDTO(String username) {
        this.username = username;
    }
}
