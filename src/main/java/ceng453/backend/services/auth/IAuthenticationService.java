package ceng453.backend.services.auth;

import ceng453.backend.models.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationService {

    /**
     * It will check if the username and password is correct. If it is correct, it will return the token.
     * @param username It is the unique username of the user
     * @param password It is the password of the user
     * @return A base response defined in the BaseResponse class
     */
    ResponseEntity<BaseResponse> login(String username, String password);

    /**
     * It will check if the token is valid. If it is valid, it will return the success message and will email to user's email.
     * @param username It is the unique username of the user
     * @param email   It is the email of the user
     * @param password It is the password of the user
     * @param passwordRememberQuestion It is the password remember question of the user. When user request it, it will be sent to the user
     * @return A base response defined in the BaseResponse class
     */
    ResponseEntity<BaseResponse> register(
            String username,
            String email,
            String password,
            String passwordRememberQuestion);

    /**
     * It will check if the token is valid. If it is valid, it will return the success message and logout the user.
     * @param token It is the token of the user with expiration time.
     * @return A base response defined in the BaseResponse class
     */
    ResponseEntity<BaseResponse> logout(String token);

    /**
     * It will remind the password of the user determined at the registration of the corresponding user.
     * @param username It is the unique username of the user
     * @return A base response defined in the BaseResponse class
     */
    ResponseEntity<BaseResponse> remindPassword(String username);

    /**
     * It will start the procedure of password reset of the user and send a token for resetting his/her password.
     * The token will be sent via an email.
     * @param username It is the unique username of the user
     * @return
     */
    ResponseEntity<BaseResponse> resetPasswordRequest(String username);

    /**
     * It will reset the password of the user determined at the registration of the corresponding user.
     * @param username It is the unique username of the user
     * @param password It is new password of the user
     * @param token It is the token sent in the email.
     * @return
     */
    ResponseEntity<BaseResponse> resetPassword(String username, String password, String token);
}
