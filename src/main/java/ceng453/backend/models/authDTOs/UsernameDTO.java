package ceng453.backend.models.authDTOs;

import io.swagger.annotations.ApiModelProperty;

public class UsernameDTO {
    @ApiModelProperty(value = "The unique username", example = "yigitucan")
    public String username;
}
