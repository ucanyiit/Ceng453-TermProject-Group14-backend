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


    @Override
    public ResponseEntity<BaseResponse> login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new BaseResponse(false, "Cannot find the username", "")
                    .prepareResponse(HttpStatus.UNAUTHORIZED);

        } else if (!user.getPassword().equals(password)) {
            return new BaseResponse(false, "Wrong password", "")
                    .prepareResponse(HttpStatus.UNAUTHORIZED);
        } else {
            return new BaseResponse(true, "", createToken(username))
                    .prepareResponse(HttpStatus.OK);
        }
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
                .setExpiration(new Date(System.currentTimeMillis() + 86_400_000)) // 1 day
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

        return new BaseResponse(true, "You have registered successfully", "")
                .prepareResponse(HttpStatus.OK);    }

    @Override
    public ResponseEntity<BaseResponse> remindPassword(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new BaseResponse(false, "No user is found with the given username.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        String passwordReminder = user.getPasswordReminder();
        if (passwordReminder == null) {
            return new BaseResponse(false, "No password reminder found for this user.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        return new BaseResponse(false, passwordReminder, "").prepareResponse(HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<BaseResponse> logout(String token) {
        return null;
    }


}
