package com.ivan_rodrigues.kata.tictactoe.service;

import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardFieldSymbol;
import com.ivan_rodrigues.kata.tictactoe.model.data.Game;
import com.ivan_rodrigues.kata.tictactoe.model.request.PlayMove;

import java.util.List;

public interface GameService {
    List<Game> getAll();

    Game getById(String uuid);

    Game create(Game game);

    void deleteById(String uuid);

    void checkMoveValidity(Game game, PlayMove playMove);
    Game updateGameState(Game game, PlayMove playMove);
    boolean isGameFinished(Game game, BoardFieldSymbol activePlayerSymbol, String activePlayer);

}
