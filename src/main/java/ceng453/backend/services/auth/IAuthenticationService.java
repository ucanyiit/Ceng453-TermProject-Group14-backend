package ceng453.backend.services.auth;

import ceng453.backend.models.BaseResponse;
import io.swagger.annotations.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.ws.Response;

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
}
