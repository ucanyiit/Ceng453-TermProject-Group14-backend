package ceng453.backend.services.auth;

import ceng453.backend.models.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationService {

    /**
     * @param username
     * @param password
     * @return
     */
    ResponseEntity<BaseResponse> login(String username, String password);

    ResponseEntity<BaseResponse> register(
            String username,
            String email,
            String password,
            String passwordRememberQuestion);

    ResponseEntity<BaseResponse> logout(String token);

    ResponseEntity<BaseResponse> remindPassword(String username);
}
