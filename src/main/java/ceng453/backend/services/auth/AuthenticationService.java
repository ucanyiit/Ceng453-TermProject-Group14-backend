package ceng453.backend.services.auth;

import ceng453.backend.models.BaseResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    /**
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResponseEntity<BaseResponse> login(String username, String password) {

        return null;
    }

    @Override
    public ResponseEntity<BaseResponse> register(String username, String email, String password, String passwordRememberQuestion) {
        return null;
    }

    @Override
    public ResponseEntity<BaseResponse> logout(String token) {
        return null;
    }
}
