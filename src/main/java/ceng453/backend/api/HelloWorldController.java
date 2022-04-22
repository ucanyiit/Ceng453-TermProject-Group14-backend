package ceng453.backend.api;

import ceng453.backend.models.ResponseModels.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
public class HelloWorldController {
    @GetMapping(produces = "application/json")
    public Object helloWorld() {
        return new BaseResponse(true, "ping").prepareResponse(HttpStatus.OK);
    }
}
