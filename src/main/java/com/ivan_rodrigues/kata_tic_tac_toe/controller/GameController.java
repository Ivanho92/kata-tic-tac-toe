package com.ivan_rodrigues.kata_tic_tac_toe.controller;

import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.NewGame;
import com.ivan_rodrigues.kata_tic_tac_toe.model.PlayMove;
import com.ivan_rodrigues.kata_tic_tac_toe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Game createNewGame(@RequestBody NewGame newGame) {
        return gameService.create(new Game(newGame.getPlayerX(), newGame.getPlayerO()));
    }

    @PutMapping("/games/{uuid}/play")
    public Game makePlayMove(@PathVariable String uuid, @RequestBody PlayMove playMove) {
        return gameService.updateGameState(gameService.getById(uuid), playMove);
    }

    @DeleteMapping("/games/{uuid}")
    public ResponseEntity deleteGameById(@PathVariable String uuid) {
        return gameService.deleteById(uuid);
    }
}

