package ceng453.backend.models.authDTOs;

import io.swagger.annotations.ApiModelProperty;

public class RegisterDTO {
    @ApiModelProperty(value = "The unique username", example = "yigitucan")
    public String username;

    @ApiModelProperty(value = "The unique email", example = "ucanyiit@gmail.com")
    public String password;

    @ApiModelProperty(value = "The password that contains more than 8 character", example = "ucanucan")
    public String email;

    @ApiModelProperty(value = "A reminder to user for her/his password in case of she/he forgot", example = "surname multiplied by two")
    public String passwordReminder;
}
