package com.ivan_rodrigues.kata_tic_tac_toe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.PlayMove;
import com.ivan_rodrigues.kata_tic_tac_toe.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnAllGames() throws Exception {
        this.mockMvc.perform(get("/api/games")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOneGame() throws Exception {
        String id = "04511a48-0607-41b7-9254-313ebf88693c";
        this.mockMvc.perform(get("/api/games/" + id)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNoGame_notFound_CorrectIDFormat() throws Exception {
        String id = "04511a48-0607-41b7-9254-313ebf88693d";
        this.mockMvc.perform(get("/api/games/" + id)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNoGame_notFound_NotValidIDFormat() throws Exception {
        String id = "04511a48-06";
        this.mockMvc.perform(
                        get("/api/games/" + id))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid UUID string: " + id));
    }

    @Test
    public void shouldCreateNewGame() throws Exception {
        // Creating a new game request body and Converting it to JSON
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new Game("John Doe", "Chuck Norris"));

        this.mockMvc
                .perform(post("/api/games/").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NEW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nextPlayerSymbol").value("X"));
        ;
    }

    @Test
    public void shouldDeleteAnExistingGame() throws Exception {
        Game game = new Game("Bob", "Alice");
        UUID uuid = game.getUuid();
        gameService.create(game);

        this.mockMvc
                .perform(delete("/api/games/" + uuid))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The game with id " + uuid + " has been successfully deleted!"));
    }

    @Test
    public void shouldUpdateGame_validMove_ongoing() throws Exception {
        HashMap<Board.Field, Board.FieldSymbol> fields = new HashMap<>();
        fields.put(Board.Field.A1, Board.FieldSymbol.X);
        fields.put(Board.Field.A2, Board.FieldSymbol.O);
        fields.put(Board.Field.A3, null);
        fields.put(Board.Field.B1, null);
        fields.put(Board.Field.B2, null);
        fields.put(Board.Field.B3, null);
        fields.put(Board.Field.C1, null);
        fields.put(Board.Field.C2, null);
        fields.put(Board.Field.C3, null);

        Game game = new Game("Bob", "Alice", new Board(fields));
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new PlayMove("Bob", Board.Field.A3));

        this.mockMvc.perform(put("/api/games/" + uuid + "/play").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ONGOING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nextPlayerSymbol").value("O"));
    }

    @Test
    public void shouldNotUpdateGame_notValidMove_fieldAlreadyOccupied() throws Exception {
        HashMap<Board.Field, Board.FieldSymbol> fields = new HashMap<>();
        fields.put(Board.Field.A1, Board.FieldSymbol.X);
        fields.put(Board.Field.A2, Board.FieldSymbol.O);
        fields.put(Board.Field.A3, null);
        fields.put(Board.Field.B1, null);
        fields.put(Board.Field.B2, null);
        fields.put(Board.Field.B3, null);
        fields.put(Board.Field.C1, null);
        fields.put(Board.Field.C2, null);
        fields.put(Board.Field.C3, null);

        Game game = new Game("Bob", "Alice", new Board(fields));
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new PlayMove("Bob", Board.Field.A2));

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Field A2 already occupied! (value = O)"));
    }

    @Test
    public void shouldNotUpdateGame_notValidMove_wrongPlayer() throws Exception {

        Game game = new Game("Bob", "Alice");
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new PlayMove("Alice", Board.Field.A2));

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Wrong player! Next player to play is: Bob"));
    }

    @Test
    public void shouldEndGame_draw() throws Exception {

        HashMap<Board.Field, Board.FieldSymbol> fields = new HashMap<>();
        fields.put(Board.Field.A1, Board.FieldSymbol.O);
        fields.put(Board.Field.A2, Board.FieldSymbol.X);
        fields.put(Board.Field.A3, Board.FieldSymbol.X);
        fields.put(Board.Field.B1, Board.FieldSymbol.X);
        fields.put(Board.Field.B2, Board.FieldSymbol.O);
        fields.put(Board.Field.B3, Board.FieldSymbol.O);
        fields.put(Board.Field.C1, Board.FieldSymbol.O);
        fields.put(Board.Field.C2, null);
        fields.put(Board.Field.C3, null);

        Game game = new Game("Player 1", "Player 2", new Board(fields));
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody_firstMove = mapper.writeValueAsString(new PlayMove("Player 1", Board.Field.C3));
        String requestBody_secondMove = mapper.writeValueAsString(new PlayMove("Player 2", Board.Field.C2));

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody_firstMove))
                .andExpect(status().is(200));

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody_secondMove))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("FINISHED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outcome").value("DRAW"));
    }

    @Test
    public void shouldEndGame_win() throws Exception {

        HashMap<Board.Field, Board.FieldSymbol> fields = new HashMap<>();
        fields.put(Board.Field.A1, Board.FieldSymbol.X);
        fields.put(Board.Field.A2, null);
        fields.put(Board.Field.A3, Board.FieldSymbol.O);
        fields.put(Board.Field.B1, Board.FieldSymbol.O);
        fields.put(Board.Field.B2, Board.FieldSymbol.O);
        fields.put(Board.Field.B3, null);
        fields.put(Board.Field.C1, null);
        fields.put(Board.Field.C2, Board.FieldSymbol.X);
        fields.put(Board.Field.C3, Board.FieldSymbol.X);

        Game game = new Game("Player 1", "Player 2", new Board(fields));
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new PlayMove("Player 1", Board.Field.C1));

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("FINISHED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outcome").value("WIN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner").value("Player 1"));
    }

    // TODO
//    @Test
//    public void shouldUpdateGame_notValidMove_nonExistingField() throws Exception {
//
//        Game game = new Game("Bob", "Alice");
//        UUID uuid = game.getUuid();
//
//        gameService.create(game);
//
//        ObjectMapper mapper = new ObjectMapper();
//        String requestBody = mapper.writeValueAsString(new PlayMove("Alice", (Board.Field) "D4"));
//
//        this.mockMvc.perform(
//                        put("/api/games/" + uuid + "/play")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(requestBody))
//                .andDo(print())
//                .andExpect(status().is(400));
//
////                .andExpect(content().string("{\"message\":\"Wrong player! Next player to play is: Bob\",\"status\":\"error\"}"));
//    }
}
