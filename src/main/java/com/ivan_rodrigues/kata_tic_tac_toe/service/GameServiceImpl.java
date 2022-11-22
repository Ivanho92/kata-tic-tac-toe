package com.ivan_rodrigues.kata_tic_tac_toe.service;

import com.ivan_rodrigues.kata_tic_tac_toe.dao.GameDAO;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDAO gameDAO;

    @Override
    public List<Game> getAll() {
        return gameDAO.fetchAll();
    }

    @Override
    public Game getById(String uuid) {
        return gameDAO.fetchById(uuid);
    }

    @Override
    public void save(Game entity) {

    }

    @Override
    public void deleteById(int id) {

    }
}
