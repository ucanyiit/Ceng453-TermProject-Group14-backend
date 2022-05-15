package ceng453.backend.api.instructions;

import ceng453.backend.models.responses.instructions.InstructionsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/api/instructions")
@RestController
public class InstructionsController {

    /**
     * Returns a list of instructions.
     */
    @GetMapping(produces = "application/json")
    public Object getInstructions() {
        List<String> instructions = Arrays.asList("instruction 1", "instruction 2");

        return new InstructionsResponse(true, "Instructions List", instructions).prepareResponse(HttpStatus.OK);
    }
}
