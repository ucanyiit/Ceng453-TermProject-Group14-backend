package ceng453.backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ceng453.backend.models.BaseResponse;

@RequestMapping("/")
@RestController
public class HelloWorldController {
    @GetMapping(produces = "application/json")
    public Object helloWorld() {
        return new BaseResponse(true, "test ", "burda ").prepareResponse(HttpStatus.OK);
    }
}
