package com.ivan_rodrigues.kata.tictactoe.dao;

import com.ivan_rodrigues.kata.tictactoe.model.data.Game;

import java.util.List;

public interface GameDAO {
    List<Game> fetchAll();

    Game fetchById(String uuid);

    boolean containsId(String uuid);

    Game save(Game game);

    void deleteById(String uuid);
}
