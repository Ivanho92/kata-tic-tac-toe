package com.ivan_rodrigues.kata_tic_tac_toe.dao;

import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameDAOImpl implements GameDAO {
    @Override
    public List<Game> fetchAll() {
        return null;
    }

    @Override
    public Game fetchById(int id) {
        return null;
    }

    @Override
    public void save(Game entity) {

    }

    @Override
    public void deleteById(int id) {

    }
}
