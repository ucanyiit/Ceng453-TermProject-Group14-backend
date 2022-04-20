package ceng453.backend.services.auth;

import ceng453.backend.models.BaseResponse;
import ceng453.backend.models.User;
import ceng453.backend.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JavaMailSender emailSender;

    HashMap<String, Pair<String, Date>> tokenMap = new HashMap<>();

    @Override
    public ResponseEntity<BaseResponse> login(String username, String password) {
        if (username == null || password == null) {
            return new BaseResponse(false, "Username and password are required.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

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
                .setId("monopoly-token")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86_400_000)) // 1 day
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }

    @Override
    public ResponseEntity<BaseResponse> register(String username, String email, String password, String passwordReminder) {
        if (username == null || email == null || password == null || passwordReminder == null) {
            return new BaseResponse(false, "Username, email, password and a password reminder are required.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setPasswordReminder(passwordReminder);
            userRepository.save(user);
        } catch (Exception e) {
            return new BaseResponse(false, "Username or email already exists.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ucanyiittest@gmail.com");
            message.setTo(email);
            message.setSubject("Monopoly - Hello!");
            message.setText("Welcome to Project Monopoly, " + username + ". Thank you for registering.");
            emailSender.send(message);
        } catch (Exception e) {
            return  new BaseResponse(false, "Cannot send email", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        return new BaseResponse(true, "You have registered successfully", "")
                .prepareResponse(HttpStatus.OK);    }

    @Override
    public ResponseEntity<BaseResponse> remindPassword(String username) {
        if (username == null) {
            return new BaseResponse(false, "Username is required.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new BaseResponse(false, "No user is found with the given username.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        String passwordReminder = user.getPasswordReminder();
        if (passwordReminder == null) {
            return new BaseResponse(false, "No password reminder found for this user.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        return new BaseResponse(true, passwordReminder, "").prepareResponse(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<BaseResponse> resetPasswordRequest(String username) {
        if (username == null) {
            return new BaseResponse(false, "Username is required.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new BaseResponse(false, "No user is found with the given username.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        // Generate a token here
        String token = createToken(user.getUsername());
        tokenMap.put(user.getUsername(), new Pair<>(token, new Date(System.currentTimeMillis() + 86_400_000)));

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ucanyiittest@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject("Monopoly - Password Reset");
            String passwordResetLink = "http://localhost:8080/api/auth/reset-password?token=" + token + "&username=" + username + "&password=" + "new-password";
            message.setText("You can reset your password using " + passwordResetLink + ".");
            emailSender.send(message);
        } catch (Exception e) {
            return new BaseResponse(false, "Error sending email.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        return new BaseResponse(true, "A password reset link is sent to your mail.", "").prepareResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseResponse> resetPassword(String username, String password, String token) {
        if (username == null || password == null || token == null) {
            return new BaseResponse(false, "Username, password and token are required.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new BaseResponse(false, "No user is found with the given username.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        // Check generated token here
        try {
            Pair<String, Date> tokenPair = tokenMap.get(username);
            boolean check = token.equals(tokenPair.getValue(0)) && ((Date) tokenPair.getValue(1)).after(new Date(System.currentTimeMillis()));
            tokenMap.remove(username);

            if (check) {
                user.setPassword(password);
                userRepository.save(user);
                return new BaseResponse(true, "Password has been reset successfully.", "").prepareResponse(HttpStatus.OK);
            }
            else {
                return new BaseResponse(false, "Invalid token.", "").prepareResponse(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new BaseResponse(false, "Invalid token.", "").prepareResponse(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> logout(String token) {
        return null;
    }


}
