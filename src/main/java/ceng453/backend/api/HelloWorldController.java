package ceng453.backend.api;

import ceng453.backend.models.User;
import ceng453.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ceng453.backend.models.BaseResponse;

@RequestMapping("/")
@RestController
public class HelloWorldController {

    @Autowired
    private UserRepository userRepository;

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
        return "Saved";
    }
}
