package ceng453.backend.services.auth;

import ceng453.backend.models.BaseResponse;
import ceng453.backend.models.User;
import ceng453.backend.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        String token = createToken(username);
        return new BaseResponse(true, "" , token).prepareResponse(HttpStatus.OK);
    }

    private String createToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600_000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
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
