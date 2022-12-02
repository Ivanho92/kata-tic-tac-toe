package com.ivan_rodrigues.kata.tictactoe.controller;

import com.ivan_rodrigues.kata.tictactoe.exception.NotFoundException;
import com.ivan_rodrigues.kata.tictactoe.model.data.Board;
import com.ivan_rodrigues.kata.tictactoe.model.data.Game;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardFieldSymbol;
import com.ivan_rodrigues.kata.tictactoe.model.request.NewGame;
import com.ivan_rodrigues.kata.tictactoe.model.request.PlayMove;
import com.ivan_rodrigues.kata.tictactoe.service.GameService;
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

    private GameService gameService;

    @Autowired
    public GameController (GameService gameService) {
        this.gameService = gameService;
    }

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

        gameService.checkMoveValidity(game, playMove);

        BoardFieldSymbol activePlayerSymbol = game.getNextPlayerSymbol();
        gameService.updateGameState(game, playMove);

        gameService.isGameFinished(game, activePlayerSymbol, playMove.getPlayer());

        return game;
    }

    @DeleteMapping("/games/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameById(@PathVariable String uuid) {
        gameService.deleteById(uuid);
    }
}

