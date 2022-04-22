package ceng453.backend.models.authDTOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LoginDTO extends UsernameDTO{
    @ApiModelProperty(value = "The unique username", example = "yigitucan")
    private String username;

    @ApiModelProperty(value = "The unique email", example = "ucanyiit@gmail.com")
    private String password;

    public LoginDTO(String username) {
        super(username);
    }
}
