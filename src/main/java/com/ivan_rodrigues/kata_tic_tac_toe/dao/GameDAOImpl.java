package com.ivan_rodrigues.kata_tic_tac_toe.dao;

import com.ivan_rodrigues.kata_tic_tac_toe.model.data.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class GameDAOImpl implements GameDAO {
    private List<Game> games;

    public GameDAOImpl() {
        this.games = new ArrayList<>();
    }

    @Override
    public List<Game> fetchAll() {
        return games;
    }

    @Override
    public Game fetchById(String uuid) {
        List<Game> foundGame = games.stream().filter(game -> game.getUuid().equals(UUID.fromString(uuid))).collect(Collectors.toList());

        if (foundGame.size() > 1) {
            throw new IllegalStateException("Found more than one game with the same ID.");
        };

        if (foundGame.size() == 0) {
            return null;
        };

        return foundGame.get(0);
    }

    @Override
    public Game save(Game game) {
        games.add(game);
        return game;
    }

    @Override
    public boolean deleteById(String uuid) {
        games.remove(fetchById(uuid));
        return true;
    }
}
