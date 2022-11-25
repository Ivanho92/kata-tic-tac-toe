package com.ivan_rodrigues.kata_tic_tac_toe.controller;

import com.ivan_rodrigues.kata_tic_tac_toe.model.data.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.data.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.request.NewGame;
import com.ivan_rodrigues.kata_tic_tac_toe.model.request.PlayMove;
import com.ivan_rodrigues.kata_tic_tac_toe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameService.getAll();
    }

    @GetMapping("/games/{uuid}")
    public Game getGameById(@PathVariable String uuid) {
        return gameService.getById(uuid);
    }

    @PostMapping("/games")
    public Game createNewGame(@Valid @RequestBody NewGame newGame) {
        return gameService.create(new Game(newGame.getPlayerX(), newGame.getPlayerO()));
    }

    @PutMapping("/games/{uuid}/play")
    public Game makePlayMove(@PathVariable String uuid, @RequestBody PlayMove playMove) {
        Game game = gameService.getById(uuid);

        if (game == null) {
            throw new IllegalArgumentException("No game found with id " + uuid);
        }

        String activePlayer = playMove.getPlayer();
        String nextPlayer = game.getNextPlayer();
        Board.FieldSymbol activeSymbol = game.getNextPlayerSymbol();

        if (!activePlayer.equals(nextPlayer)) {
            throw new IllegalArgumentException("Wrong player! Next player to play is: " + nextPlayer);
        }

        Board.Field playedField = playMove.getField();
        Board.FieldSymbol boardField = game.getBoard().getFields().get(playedField);

        if (boardField != null) {
            throw new IllegalArgumentException("Field " + playedField + " already occupied! (value = " + boardField + ")");
        }

        gameService.updateGameState(game, playMove);
        gameService.isGameFinished(game, activeSymbol, activePlayer);
//        if(gameService.isGameFinished(game, activeSymbol, activePlayer)) {
//            if (game.getWinner() != null) {
//                System.out.println("Game over! Player " + game.getWinner() + " wins!");
//            } else {
//                System.out.println("Game over! It's a draw.");
//            }
//        };

        return game;
    }

    @DeleteMapping("/games/{uuid}")
    public ResponseEntity deleteGameById(@PathVariable String uuid) {
        Map<String, Object> response = new HashMap<>();

        if (!gameService.deleteById(uuid)) {
            response.put("status", "error");
            response.put("message", "Something went wrong while deleting game with id " + uuid);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("status", "success");
        response.put("message", "The game with id " + uuid + " has been successfully deleted!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

