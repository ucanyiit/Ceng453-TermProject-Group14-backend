package ceng453.backend.models.authDTOs;

import io.swagger.annotations.ApiModelProperty;

public class LoginDTO {
    @ApiModelProperty(value = "The unique username", example = "yigitucan")
    public String username;

    @ApiModelProperty(value = "The unique email", example = "ucanyiit@gmail.com")
    public String password;
}
