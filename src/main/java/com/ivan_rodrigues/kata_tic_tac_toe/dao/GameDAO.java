package com.ivan_rodrigues.kata_tic_tac_toe.dao;

import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;

import java.util.List;

public interface GameDAO {
    List<Game> fetchAll();

    Game fetchById(int id);

    void save(Game entity);

    void deleteById(int id);
}
