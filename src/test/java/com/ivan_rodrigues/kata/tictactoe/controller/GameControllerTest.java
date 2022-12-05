package com.ivan_rodrigues.kata.tictactoe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan_rodrigues.kata.tictactoe.model.data.Board;
import com.ivan_rodrigues.kata.tictactoe.model.data.Game;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardField;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardFieldSymbol;
import com.ivan_rodrigues.kata.tictactoe.model.request.PlayMove;
import com.ivan_rodrigues.kata.tictactoe.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class GameControllerTest {

    private GameService gameService;
    private MockMvc mockMvc;

    @Autowired
    public GameControllerTest(GameService gameService, MockMvc mockMvc) {
        this.gameService = gameService;
        this.mockMvc = mockMvc;
    }

    @Test
    public void shouldReturnAllGames() throws Exception {
        Game game1 = new Game("Bob", "Alice", new Board());
        Game game2 = new Game("Julien", "Ivan", new Board());
        Game game3 = new Game("Quentin", "Elise", new Board());

        gameService.create(game1);
        gameService.create(game2);
        gameService.create(game3);

        this.mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(not(empty()))));
    }

    @Test
    public void shouldReturnOneGame() throws Exception {
        Game game = new Game("Bob", "Alice", new Board());
        UUID uuid = game.getUuid();

        gameService.create(game);

        this.mockMvc.perform(get("/api/games/" + uuid))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").value(uuid.toString()));
    }

    @Test
    public void shouldReturnNoGame_notFound_CorrectIDFormat() throws Exception {
        String uuid = "04511a48-0607-41b7-9254-313ebf88693d";
        this.mockMvc.perform(get("/api/games/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Game with id " + uuid + " not found.", result.getResolvedException().getMessage()));
    }

    @Test
    public void shouldReturnNoGame_notFound_NotValidIDFormat() throws Exception {
        String uuid = "04511a48-06";
        this.mockMvc.perform(
                        get("/api/games/" + uuid))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Invalid UUID.", result.getResolvedException().getMessage()));
    }

    @Test
    public void shouldCreateNewGame() throws Exception {
        // Creating a new game request body and Converting it to JSON
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new Game("John Doe", "Chuck Norris"));

        this.mockMvc
                .perform(post("/api/games/").contentType(MediaType.APPLICATION_JSON).content(requestBody))
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
                .andExpect(status().is(204));
    }

    @Test
    public void shouldUpdateGame_validMove_ongoing() throws Exception {
        HashMap<BoardField, BoardFieldSymbol> fields = new HashMap<>();
        fields.put(BoardField.A1, BoardFieldSymbol.X);
        fields.put(BoardField.A2, BoardFieldSymbol.O);
        fields.put(BoardField.A3, null);
        fields.put(BoardField.B1, null);
        fields.put(BoardField.B2, null);
        fields.put(BoardField.B3, null);
        fields.put(BoardField.C1, null);
        fields.put(BoardField.C2, null);
        fields.put(BoardField.C3, null);

        Game game = new Game("Bob", "Alice", new Board(fields));
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new PlayMove("Bob", BoardField.A3));

        this.mockMvc.perform(put("/api/games/" + uuid + "/play").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ONGOING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nextPlayerSymbol").value("O"));
    }

    @Test
    public void shouldNotUpdateGame_notValidMove_fieldAlreadyOccupied() throws Exception {
        HashMap<BoardField, BoardFieldSymbol> fields = new HashMap<>();
        fields.put(BoardField.A1, BoardFieldSymbol.X);
        fields.put(BoardField.A2, BoardFieldSymbol.O);
        fields.put(BoardField.A3, null);
        fields.put(BoardField.B1, null);
        fields.put(BoardField.B2, null);
        fields.put(BoardField.B3, null);
        fields.put(BoardField.C1, null);
        fields.put(BoardField.C2, null);
        fields.put(BoardField.C3, null);

        Game game = new Game("Bob", "Alice", new Board(fields));
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new PlayMove("Bob", BoardField.A2));

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertEquals("Field A2 already occupied! (value = O)", result.getResolvedException().getMessage()));
    }

    @Test
    public void shouldNotUpdateGame_notValidMove_wrongPlayer() throws Exception {
        Game game = new Game("Bob", "Alice");
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new PlayMove("Alice", BoardField.A2));

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertEquals("Wrong player! Next player to play is: Bob", result.getResolvedException().getMessage()));
    }

    @Test
    public void shouldEndGame_draw() throws Exception {
        HashMap<BoardField, BoardFieldSymbol> fields = new HashMap<>();
        fields.put(BoardField.A1, BoardFieldSymbol.O);
        fields.put(BoardField.A2, BoardFieldSymbol.X);
        fields.put(BoardField.A3, BoardFieldSymbol.X);
        fields.put(BoardField.B1, BoardFieldSymbol.X);
        fields.put(BoardField.B2, BoardFieldSymbol.O);
        fields.put(BoardField.B3, BoardFieldSymbol.O);
        fields.put(BoardField.C1, BoardFieldSymbol.O);
        fields.put(BoardField.C2, null);
        fields.put(BoardField.C3, null);

        Game game = new Game("Player 1", "Player 2", new Board(fields));
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody_firstMove = mapper.writeValueAsString(new PlayMove("Player 1", BoardField.C3));
        String requestBody_secondMove = mapper.writeValueAsString(new PlayMove("Player 2", BoardField.C2));

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody_firstMove))
                .andExpect(status().is(200));

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody_secondMove))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("FINISHED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outcome").value("DRAW"));
    }

    @Test
    public void shouldEndGame_win() throws Exception {
        HashMap<BoardField, BoardFieldSymbol> fields = new HashMap<>();
        fields.put(BoardField.A1, BoardFieldSymbol.X);
        fields.put(BoardField.A2, null);
        fields.put(BoardField.A3, BoardFieldSymbol.O);
        fields.put(BoardField.B1, BoardFieldSymbol.O);
        fields.put(BoardField.B2, BoardFieldSymbol.O);
        fields.put(BoardField.B3, null);
        fields.put(BoardField.C1, null);
        fields.put(BoardField.C2, BoardFieldSymbol.X);
        fields.put(BoardField.C3, BoardFieldSymbol.X);

        Game game = new Game("Player 1", "Player 2", new Board(fields));
        UUID uuid = game.getUuid();

        gameService.create(game);

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(new PlayMove("Player 1", BoardField.C1));

        System.out.println("game" + game);

        this.mockMvc.perform(
                        put("/api/games/" + uuid + "/play")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("FINISHED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outcome").value("WIN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner").value("Player 1"));
    }
}


