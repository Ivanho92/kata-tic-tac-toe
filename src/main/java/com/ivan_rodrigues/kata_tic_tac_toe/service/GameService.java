package com.ivan_rodrigues.kata_tic_tac_toe.service;

import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.NewGame;
import com.ivan_rodrigues.kata_tic_tac_toe.model.PlayMove;

import java.util.List;

public interface GameService {
    List<Game> getAll();

    Game getById(String uuid);

    Game updateGameState(Game game, PlayMove playMove);

    Game create(Game game);

    void deleteById(int id);
}
