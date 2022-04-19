package ceng453.backend.services.auth;

import ceng453.backend.models.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private JavaMailSender emailSender;

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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ucanyiittest@gmail.com");
        message.setTo(email);
        message.setSubject("Account Activation");
        message.setText("Welcome to Project Monopoly " + username + ", activate your account here: ");
        emailSender.send(message);

        return null;
    }

    @Override
    public ResponseEntity<BaseResponse> logout(String token) {
        return null;
    }
}
