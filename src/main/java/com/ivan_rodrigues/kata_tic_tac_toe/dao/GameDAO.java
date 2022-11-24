package com.ivan_rodrigues.kata_tic_tac_toe.dao;

import com.ivan_rodrigues.kata_tic_tac_toe.model.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;

import java.util.List;

public interface GameDAO {
    List<Game> fetchAll();

    Game fetchById(String uuid);

    Game save(Game game);

    void deleteById(int id);
}
