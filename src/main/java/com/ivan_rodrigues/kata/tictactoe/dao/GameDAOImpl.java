package com.ivan_rodrigues.kata.tictactoe.dao;

import com.ivan_rodrigues.kata.tictactoe.exception.BadRequestException;
import com.ivan_rodrigues.kata.tictactoe.exception.InternalServerErrorException;
import com.ivan_rodrigues.kata.tictactoe.model.data.Game;
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
        List<Game> foundGame;

        try  {
            foundGame = games.stream().filter(g -> g.getUuid().equals(UUID.fromString(uuid))).collect(Collectors.toList());
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException("Invalid UUID.");
        }

        if (foundGame.size() > 1) {
            throw new InternalServerErrorException("Found " + foundGame.size() + " games with the same ID! (" + uuid + ").");
        }

        if (foundGame.size() == 0) {
            return null;
        }

        return foundGame.get(0);
    }

    @Override
    public boolean containsId(String uuid) {
        return games.stream().anyMatch(g -> g.getUuid().equals(UUID.fromString(uuid)));
    }

    @Override
    public Game save(Game game) {
        games.add(game);
        return game;
    }

    @Override
    public void deleteById(String uuid) {
        games.remove(fetchById(uuid));
    }
}
