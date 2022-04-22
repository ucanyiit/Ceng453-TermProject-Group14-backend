package ceng453.backend.api.auth;

import ceng453.backend.models.BaseResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ceng453.backend.services.auth.*;
import lombok.RequiredArgsConstructor;

@RequestMapping("api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    // Injection
    private final IAuthenticationService authService;

    /**
     * This method is used to login a user.
     * @param username: The unique username of the user.
     * @param password: The password of the user.
     * @return A response entity with the token of the user if successful.
     */
    @ApiOperation(
            value = "Login",
            notes = "Login with username and password",
            response = ResponseEntity.class
    )
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(
            @ApiParam(value = "The unique username", example = "bahadirkisbet")
            @RequestBody String username,
            @ApiParam(value = "The password consisting of more than 8 characters", example = "kisbetbahadir")
            @RequestBody String password) {
        return authService.login(username, password);
    }

    /**
     * This method is used to logout a user.
     * @param token: The token of the user.
     * @return true if successful, otherwise false.
     */
    @GetMapping("/logout")
    public ResponseEntity<BaseResponse> logout(
            @ApiParam(value = "The access token given to the user during login") @RequestBody String token) {
        return authService.logout(token);
    }

    /**
     * This method is used to register a user.
     * @param username: The unique username of the user.
     * @param email: The email of the user.
     * @param password: The password of the user.
     * @param passwordReminder: The password remember question set by the user.
     * @return true if successful, otherwise false.
     */
    @ApiOperation(
            value = "Register",
            notes = "Register with username and password",
            response = ResponseEntity.class
    )
    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(
            @ApiParam(value = "The unique username", example = "yigitucan")
            @RequestBody String username,
            @ApiParam(value = "The unique email", example = "ucanyiit@gmail.com")
            @RequestBody String email,
            @ApiParam(value = "The password that contains more than 8 character", example = "ucanucan")
            @RequestBody String password,
            @ApiParam(value = "A reminder to user for her/his password in case of she/he forgot", example = "surname multiplied by two")
            @RequestBody String passwordReminder) {
        return authService.register(username, email, password, passwordReminder);
    }

    /**
     * This method is used to login a user.
     * @param username: The unique username of the user.
     * @return A response entity with the token of the user if successful.
     */
    @ApiOperation(
            value = "Remind Password",
            notes = "Remind the user password with the given username using password reminder",
            response = ResponseEntity.class
    )
    @PostMapping("/remind-password")
    public ResponseEntity<BaseResponse> remindPassword(
            @ApiParam(value = "The unique username", example = "bahadirkisbet")
            @RequestBody String username) {
        return authService.remindPassword(username);
    }

    /**
     * This method is used to reset user's password.
     * @param username: The unique username of the user.
     * @param password: The password of the user.
     * @param token: The token of the user that's sent to their mail.
     * @return true if successful, otherwise false.
     */
    @ApiOperation(
            value = "Reset Password",
            notes = "Resets the user password with the given username and password",
            response = ResponseEntity.class
    )
    @GetMapping("/reset-password")
    public ResponseEntity<BaseResponse> changePassword(
            @ApiParam(value = "The unique username", example = "yigitucan")
            @RequestBody String username,
            @ApiParam(value = "The new password that contains more than 8 character", example = "yiityiit")
            @RequestBody String password,
            @ApiParam(value = "The token sent to the user's email", example = "<some_long_and_random_string>")
            @RequestBody String token) {
        return authService.resetPassword(username, password, token);
    }

    /**
     * This method is used to request a password reset.
     * @param username: The unique username of the user.
     * @return A response entity with the "email is sent" message if successful.
     */
    @ApiOperation(
            value = "Request Password Reset",
            notes = "Request the user password reset with the given username",
            response = ResponseEntity.class
    )
    @PostMapping("/request-password-reset")
    public ResponseEntity<BaseResponse> getUserInfo(
            @ApiParam(value = "The unique username", example = "yigitucan")
            @RequestBody String username) {
        return authService.resetPasswordRequest(username);
    }

}
