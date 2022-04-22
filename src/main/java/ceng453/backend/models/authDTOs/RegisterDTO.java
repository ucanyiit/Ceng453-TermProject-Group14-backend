package ceng453.backend.models.authDTOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RegisterDTO extends LoginDTO {
    @ApiModelProperty(value = "The unique email", example = "ucanyiit@gmail.com")
    private String password;

    @ApiModelProperty(value = "The password that contains more than 8 character", example = "ucanucan")
    private String email;

    @ApiModelProperty(value = "A reminder to user for her/his password in case of she/he forgot", example = "surname multiplied by two")
    private String passwordReminder;
}
