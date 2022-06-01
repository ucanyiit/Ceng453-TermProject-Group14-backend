package ceng453.backend;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String token;

    private String gameId;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    // if a player tries to roll a dice without game id
    @Test
    public void rollDiceWithoutGameId() throws Exception {
        mockMvc.perform(get("api/game/roll-dice"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // try to roll a dice with wrong game id
    @Test
    public void rollDiceWithWrongGameId() throws Exception {
        mockMvc.perform(get("api/game/roll-dice?gameId=-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // roll 4 times so that one of them is always forbidden to roll again
    @Test
    public void rollDiceRepetitively() throws Exception {
        mockMvc.perform(get("api/game/roll-dice?gameId=" + gameId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("dice1").exists())
                .andExpect(jsonPath("dice2").exists());

        mockMvc.perform(get("api/game/roll-dice?gameId=" + gameId))
                .andExpectAll(MockMvcResultMatchers.status().isOk(), MockMvcResultMatchers.status().isForbidden());

        mockMvc.perform(get("api/game/roll-dice?gameId=" + gameId))
                .andExpectAll(MockMvcResultMatchers.status().isOk(), MockMvcResultMatchers.status().isForbidden());

        mockMvc.perform(get("api/game/roll-dice?gameId=" + gameId))
                .andExpectAll(MockMvcResultMatchers.status().isOk(), MockMvcResultMatchers.status().isForbidden());
    }

    // take action without a game id
    @Test
    public void takeActionEmptyParameters() throws Exception {
        mockMvc.perform(post("api/game/take-action"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // take action without specifying action
    @Test
    public void takeActionWithOnlyGameId() throws Exception {
        mockMvc.perform(post("api/game/take-action?gameId=" + gameId ))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // sending the same request twice
    @Test
    public void takeActionRepetitiveTwice() throws Exception {
        mockMvc.perform(post("api/game/take-action?gameId=" + gameId + "&action=2"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(post("api/game/take-action?gameId=" + gameId + "&action=2"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }
}
