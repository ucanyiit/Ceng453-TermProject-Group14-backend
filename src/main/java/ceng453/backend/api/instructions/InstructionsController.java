package ceng453.backend.api.instructions;

import ceng453.backend.models.responses.instructions.InstructionsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<InstructionsResponse> getInstructions() {
        List<String> instructions = Arrays.asList("The purpose of the game is to avoid bankrupt during the game. " +
                "\n Hence, you may be careful about paying taxes and \n spend your money on properties around the tiles you are on.\n" +
                "\n" +
                "[ At the beginning ]\n" +
                "\n" +
                "Every player starts with 1500 $ and at startPoint. \n After rolling each dice, each player is able to take an appropriate \n action based on the tile they are on.\nThose can be NO_ACTION, GO_TO_JAIL, PAY_INCOME_TAX, BUY. \n" +
                "\n" +
                "[ During the game ]\n" +
                "\n" +
                "After deciding the action, player may end their turn. \n If the two dices are the same, the player will have another chance \n to roll a dice and take an action. However, if it repeats three times, \nthe player will be sent to the jail.\n" +
                "\n" +
                "[ In the jail ]\n" +
                "\n" +
                "The player has to wait for three turns or roll a dice with the same value.\n" +
                "\n" +
                "[ End of the game ]\n" +
                "\n" +
                "The money of a player is zero or negative, \n the game immediately is finished and the scores of players will be updated.");

        return new InstructionsResponse(true, "Instructions List", instructions).prepareResponse(HttpStatus.OK);
    }
}
