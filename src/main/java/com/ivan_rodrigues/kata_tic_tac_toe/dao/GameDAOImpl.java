package com.ivan_rodrigues.kata_tic_tac_toe.dao;

import com.ivan_rodrigues.kata_tic_tac_toe.model.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class GameDAOImpl implements GameDAO {
    private List<Game> games = new ArrayList<>();

    public GameDAOImpl() {
        this.games.add(new Game("04511a48-0607-41b7-9254-313ebf88693c", "Player 1", "Player 2"));
        this.games.add(new Game("Ivan", "Elise"));
        this.games.add(new Game("John Doe", "Janne Doe"));
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

//    @Override
//    public void updateGameState(Game game, String player, Board.Field field, Board.FieldSymbol symbol) {
//        game.setUpdatedOn(System.currentTimeMillis());
//        game.setStatus(Game.Status.ONGOING);
//
//        game.getBoard().getFields().put(field, symbol);
//        game.setNextPlayerSymbol(symbol == Board.FieldSymbol.X ? Board.FieldSymbol.O : Board.FieldSymbol.X);
//        game.setNextPlayer(game.getPlayerX().equals(player) ? game.getPlayerO() : game.getPlayerX());
//    }

    @Override
    public Game save(Game game) {
        games.add(game);
        return game;
    }

    @Override
    public void deleteById(int id) {

    }
}
