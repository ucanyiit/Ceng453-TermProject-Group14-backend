package ceng453.backend.models.DTOs.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UsernameDTO {
    @ApiModelProperty(value = "The unique username", example = "yigitucan")
    private String username;
}
