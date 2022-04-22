package ceng453.backend.api;

import ceng453.backend.models.responses.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class HelloWorldController {
    @GetMapping(produces = "application/json")
    public Object helloWorld() {
        return new BaseResponse(true, "ping").prepareResponse(HttpStatus.OK);
    }
}
