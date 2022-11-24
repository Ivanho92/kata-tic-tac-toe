package com.ivan_rodrigues.kata_tic_tac_toe.service;

import com.ivan_rodrigues.kata_tic_tac_toe.dao.GameDAO;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.NewGame;
import com.ivan_rodrigues.kata_tic_tac_toe.model.PlayMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

        // All verifications are passed, proceed with updating game state and meta data
        Board.FieldSymbol activeSymbol = game.getNextPlayerSymbol();
        game.setNextPlayerSymbol(activeSymbol == Board.FieldSymbol.X ? Board.FieldSymbol.O : Board.FieldSymbol.X);

        game.setUpdatedOn(System.currentTimeMillis());
        game.setStatus(Game.Status.ONGOING);
        game.getBoard().getFields().put(playedField, activeSymbol);
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
    public ResponseEntity deleteById(String uuid) {
        Map<String, Object> response = new HashMap<>();

        if (!gameDAO.deleteById(uuid)) {
            response.put("status", "error");
            response.put("message", "Something went wrong while deleting game with id " + uuid);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("status", "success");
        response.put("message", "The game with id " + uuid + " has been successfully deleted!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
