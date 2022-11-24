package com.ivan_rodrigues.kata_tic_tac_toe.service;

import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.NewGame;
import com.ivan_rodrigues.kata_tic_tac_toe.model.PlayMove;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface GameService {
    List<Game> getAll();

    Game getById(String uuid);

    Game updateGameState(Game game, PlayMove playMove);

    Game create(Game game);

    ResponseEntity deleteById(String uuid);
}
