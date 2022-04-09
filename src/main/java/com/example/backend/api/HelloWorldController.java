package com.example.backend.api;

import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
public class HelloWorldController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String helloWorld() {
        return "Hello World";
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
