package com.ivan_rodrigues.kata_tic_tac_toe.service;

import com.ivan_rodrigues.kata_tic_tac_toe.dao.GameDAO;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.NewGame;
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
    public Game updateGameState(Game game, PlayMove playMove) {
        Board.FieldSymbol activeSymbol = game.getNextPlayerSymbol();
        String activePlayer = playMove.getPlayer();
        String nextPlayer = game.getNextPlayer();

        if (!activePlayer.equals(nextPlayer)) {
            throw new IllegalArgumentException("Wrong player! Next player to play is: " + nextPlayer);
        }

        Board.Field playedField = playMove.getField();
        Board.FieldSymbol boardField = game.getBoard().getFields().get(playedField);

        if (boardField != null) {
            throw new IllegalArgumentException("Field " + playedField + " already occupied! (value = " + boardField + ")");
        }

//        gameDAO.updateGameState(game, playMove.getPlayer(), playedField, game.getNextPlayerSymbol());

        // All verifications are passed, proceed with updating game state and meta data
        game.setUpdatedOn(System.currentTimeMillis());
        game.setStatus(Game.Status.ONGOING);
        game.getBoard().getFields().put(playedField, activeSymbol);
        game.setNextPlayerSymbol(activeSymbol == Board.FieldSymbol.X ? Board.FieldSymbol.O : Board.FieldSymbol.X);
        game.setNextPlayer(game.getPlayerX().equals(activePlayer) ? game.getPlayerO() : game.getPlayerX());

        if(game.isGameFinished(game, activeSymbol)) {
            if (game.getOutcome().equals(Game.Outcome.WIN)) {
                game.setWinner(activePlayer);
                System.out.println("Game over! Player " + activePlayer + " wins!");
            } else {
                System.out.println("Game over! It's a draw.");
            }
        };

        return game;
    }

    @Override
    public Game create(Game game) {
        return gameDAO.save(game);
    }

    @Override
    public void deleteById(int id) {

    }
}
