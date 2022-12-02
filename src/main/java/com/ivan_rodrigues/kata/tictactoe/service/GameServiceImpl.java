package com.ivan_rodrigues.kata.tictactoe.service;

import com.ivan_rodrigues.kata.tictactoe.dao.GameDAO;
import com.ivan_rodrigues.kata.tictactoe.exception.ForbiddenException;
import com.ivan_rodrigues.kata.tictactoe.exception.NotFoundException;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardField;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardFieldSymbol;
import com.ivan_rodrigues.kata.tictactoe.model.data.Game;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.GameOutcome;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.GameStatus;
import com.ivan_rodrigues.kata.tictactoe.model.request.PlayMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    public static BoardField[][] WinningConditions = {
            // Horizontal wins
            {BoardField.A1, BoardField.A2, BoardField.A3},
            {BoardField.B1, BoardField.B2, BoardField.B3},
            {BoardField.C1, BoardField.C2, BoardField.C3},

            // Vertical wins
            {BoardField.A1, BoardField.B1, BoardField.C1},
            {BoardField.A2, BoardField.B2, BoardField.C2},
            {BoardField.A3, BoardField.B3, BoardField.C3},

            // Diagonal wins
            {BoardField.A1, BoardField.B2, BoardField.C3},
            {BoardField.A3, BoardField.B2, BoardField.C1},
    };




    private GameDAO gameDAO;

    @Autowired
    public GameServiceImpl(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }




    @Override
    public List<Game> getAll() {
        return gameDAO.fetchAll();
    }

    @Override
    public Game getById(String uuid) {
        Game game = gameDAO.fetchById(uuid);

        if (game == null) {
            throw new NotFoundException("Game with id " + uuid + " not found.");
        }

        return game;
    }

    @Override
    public Game create(Game game) {
        return gameDAO.save(game);
    }

    @Override
    public void deleteById(String uuid) {
        if (!gameDAO.containsId((uuid))) {
            throw new NotFoundException("Game with ID " + uuid + " not found.");
        }

        gameDAO.deleteById(uuid);
    }




    @Override
    public void checkMoveValidity(Game game, PlayMove playMove) {
        if (!playMove.getPlayer().equals(game.getNextPlayer())) {
            throw new ForbiddenException("Wrong player! Next player to play is: " + game.getNextPlayer());
        }

        BoardFieldSymbol boardField = game.getBoard().getFields().get(playMove.getField());
        if (boardField != null) {
            throw new ForbiddenException("Field " + playMove.getField() + " already occupied! (value = " + boardField + ")");
        }
    }


    @Override
    public Game updateGameState(Game game, PlayMove playMove) {
        String activePlayer = playMove.getPlayer();
        BoardField playedField = playMove.getField();

        BoardFieldSymbol activeSymbol = game.getNextPlayerSymbol();
        game.setNextPlayerSymbol(activeSymbol == BoardFieldSymbol.X ? BoardFieldSymbol.O : BoardFieldSymbol.X);

        game.setUpdatedOn(System.currentTimeMillis());
        game.setStatus(GameStatus.ONGOING);
        game.getBoard().getFields().put(playedField, activeSymbol);
        game.setNextPlayer(game.getPlayerX().equals(activePlayer) ? game.getPlayerO() : game.getPlayerX());

        return game;
    }

    @Override
    public boolean isGameFinished(Game game, BoardFieldSymbol activePlayerSymbol, String activePlayer) {
        HashMap<BoardField, BoardFieldSymbol> fields = game.getBoard().getFields();

        // Check first if there is a winner
        if (isWinner(fields, activePlayerSymbol)) {
            game.setStatus(GameStatus.FINISHED);
            game.setOutcome(GameOutcome.WIN);
            game.setWinner(activePlayer);
            game.setNextPlayer(null);
            game.setNextPlayerSymbol(null);
            return true;
        }

        // If no winner, is the game ongoing ? If no, it's a draw;
        if (!fields.containsValue(null)) {
            game.setStatus(GameStatus.FINISHED);
            game.setOutcome(GameOutcome.DRAW);
            game.setNextPlayer(null);
            game.setNextPlayerSymbol(null);
            return true;
        }

        return false;
    }

    private boolean isWinner(HashMap<BoardField, BoardFieldSymbol> fields, BoardFieldSymbol activePlayerSymbol) {
        List<BoardField> activePlayerFields = new ArrayList<>();
        for (Map.Entry<BoardField, BoardFieldSymbol> field : fields.entrySet()) {
            if (field.getValue() != null && field.getValue().equals(activePlayerSymbol)) {
                activePlayerFields.add(field.getKey());
            }
        }

        System.out.println("activePlayerSymbol:" + activePlayerSymbol);
        System.out.println("activePlayerFields:" + activePlayerFields);

        for (BoardField[] winningCombination : WinningConditions) {
            if (activePlayerFields.containsAll(Arrays.asList(winningCombination))) {
                return true;
            }
        }

        return false;
    }
}
