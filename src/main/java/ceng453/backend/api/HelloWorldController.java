package ceng453.backend.api;

import ceng453.backend.models.*;
import ceng453.backend.repositories.GameRepository;
import ceng453.backend.repositories.ScoreRepository;
import ceng453.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
public class HelloWorldController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    @GetMapping(produces = "application/json")
    public Object helloWorld() {
        return new BaseResponse(true, "test ", "burda ").prepareResponse(HttpStatus.OK);
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody
    String addNewUser (@RequestParam String username
            , @RequestParam String email) {

        User n = new User();
        n.setUsername(username);
        n.setEmail(email);
        userRepository.save(n);

        Game g = new Game(2, GameType.SINGLEPLAYER);
        gameRepository.save(g);

        Score s = new Score(n, g, 0);
        scoreRepository.save(s);


        return "Saved";
    }
}
