package com.ivan_rodrigues.kata_tic_tac_toe.service;

import com.ivan_rodrigues.kata_tic_tac_toe.dao.GameDAO;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.PlayMove;
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
    public Game updateGameState(String uuid, PlayMove playMove) {
        Game foundGame = gameDAO.fetchById(uuid);

        String activePlayer = playMove.getPlayer();
        String nextPlayer = foundGame.getNextPlayer();

        if (!activePlayer.equals(nextPlayer)) {
            throw new IllegalArgumentException("Wrong player! Next player to play is: " + nextPlayer);
        }

        Board.Field playedField = playMove.getField();
        Board.FieldSymbol boardField = foundGame.getBoard().getFields().get(playedField);

        if (boardField != null) {
            throw new IllegalArgumentException("Field " + playedField + " already occupied! (value = "+ boardField +")");
        }

        gameDAO.updateGameState(foundGame, playMove.getPlayer(), playedField, foundGame.getNextPlayerSymbol());

        /*

        // Check winning conditions
        -- Horizontal wins --
        0 : [0, 1, 2],
        1 : [3, 4, 5],
        2 : [6, 7, 8],

        -- Vertical wins --
        3 : [0, 3, 6],
        4 : [1, 4, 7],
        5 : [2, 5, 8],

        -- Diagonal wins --
        6 : [0, 4, 8],
        7 : [2, 4, 6]

        */

        return foundGame;
    }

    @Override
    public void save(Game entity) {

    }

    @Override
    public void deleteById(int id) {

    }
}
