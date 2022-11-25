package com.ivan_rodrigues.kata_tic_tac_toe.service;

import com.ivan_rodrigues.kata_tic_tac_toe.model.data.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.data.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.request.PlayMove;

import java.util.List;

public interface GameService {
    List<Game> getAll();

    Game getById(String uuid);

    Game updateGameState(Game game, PlayMove playMove);

    Game create(Game game);

    boolean deleteById(String uuid);

    boolean isGameFinished(Game game, Board.FieldSymbol activeSymbol, String activePlayer);
}
