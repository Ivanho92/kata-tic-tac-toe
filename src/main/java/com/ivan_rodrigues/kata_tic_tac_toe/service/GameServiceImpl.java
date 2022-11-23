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
        String boardField = foundGame.getBoard().getFields().get(playedField);

        if (boardField != null) {
            throw new IllegalArgumentException("Field " + playedField + " already occupied! (value = "+ boardField +")");
        }

        return foundGame;
    }

    @Override
    public void save(Game entity) {

    }

    @Override
    public void deleteById(int id) {

    }
}
