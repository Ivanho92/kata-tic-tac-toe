package com.ivan_rodrigues.kata_tic_tac_toe.service;

import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;

import java.util.List;

public interface GameService {
    List<Game> getAll();

    Game getById(String uuid);

    void save(Game entity);

    void deleteById(int id);
}
