package ceng453.backend.api.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RequestMapping("api/auth")
@RestController
public class AuthController {

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
    public ResponseEntity<?> login(String username, String password) {
        return new ResponseEntity<>(null, null, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public String logout(String token) {
        // TODO: Remove token from the map
        return "Logged out";
    }
}
