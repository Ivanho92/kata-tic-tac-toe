package com.ivan_rodrigues.kata_tic_tac_toe.dao;

import com.ivan_rodrigues.kata_tic_tac_toe.model.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;

import java.util.List;

public interface GameDAO {
    List<Game> fetchAll();

    Game fetchById(String uuid);

    void save(Game entity);

    void deleteById(int id);

    void updateGameState(Game game, String activePlayer, Board.Field field, Board.FieldSymbol symbol);
}
