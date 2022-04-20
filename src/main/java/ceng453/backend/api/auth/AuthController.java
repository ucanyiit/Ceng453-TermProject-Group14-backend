package ceng453.backend.api.auth;

import ceng453.backend.models.BaseResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<BaseResponse> login(String username, String password) {
        return authService.login(username, password);
    }

    /**
     * This method is used to logout a user.
     * @param token: The token of the user.
     * @return true if successful, otherwise false.
     */
    @GetMapping("/logout")
    public ResponseEntity<BaseResponse> logout(String token) {
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
            String username,
            String email,
            String password,
            String passwordReminder) {
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
    public ResponseEntity<BaseResponse> remindPassword(String username) {
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
            String username,
            String password,
            String token) {
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
    public ResponseEntity<BaseResponse> getUserInfo(String username) {
        return authService.resetPasswordRequest(username);
    }

}
