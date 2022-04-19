package ceng453.backend.services.auth;

import ceng453.backend.models.BaseResponse;
import ceng453.backend.models.User;
import ceng453.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserRepository userRepository;

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
    public ResponseEntity<BaseResponse> register(String username, String email, String password, String passwordReminder) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPasswordReminder(passwordReminder);
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ucanyiittest@gmail.com");
        message.setTo(email);
        message.setSubject("Account Activation");
        message.setText("Welcome to Project Monopoly " + username + ", activate your account here: ");
        emailSender.send(message);

        return null;
    }

    @Override
    public ResponseEntity<BaseResponse> remindPassword(String username) {

        String passwordReminder = userRepository.findByUsername(username).getPasswordReminder();
        if (passwordReminder == null) {
            return new BaseResponse(false, "No password reminder found for this user", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        return new BaseResponse(false, passwordReminder, "").prepareResponse(HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<BaseResponse> logout(String token) {
        return null;
    }
}
