package com.ivan_rodrigues.kata_tic_tac_toe.service;

import com.ivan_rodrigues.kata_tic_tac_toe.dao.GameDAO;
import com.ivan_rodrigues.kata_tic_tac_toe.model.data.Board;
import com.ivan_rodrigues.kata_tic_tac_toe.model.data.Game;
import com.ivan_rodrigues.kata_tic_tac_toe.model.request.PlayMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    public static Board.Field[][] WinningConditions = {
            // Horizontal wins
            {Board.Field.A1, Board.Field.A2, Board.Field.A3},
            {Board.Field.B1, Board.Field.B2, Board.Field.B3},
            {Board.Field.C1, Board.Field.C2, Board.Field.C3},

            // Vertical wins
            {Board.Field.A1, Board.Field.B1, Board.Field.C1},
            {Board.Field.A2, Board.Field.B2, Board.Field.C2},
            {Board.Field.A3, Board.Field.B3, Board.Field.C3},

            // Diagonal wins
            {Board.Field.A1, Board.Field.B2, Board.Field.C3},
            {Board.Field.A3, Board.Field.B2, Board.Field.C1},
    };

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
        Board.Field playedField = playMove.getField();

        Board.FieldSymbol activeSymbol = game.getNextPlayerSymbol();
        game.setNextPlayerSymbol(activeSymbol == Board.FieldSymbol.X ? Board.FieldSymbol.O : Board.FieldSymbol.X);

        game.setUpdatedOn(System.currentTimeMillis());
        game.setStatus(Game.Status.ONGOING);
        game.getBoard().getFields().put(playedField, activeSymbol);
        game.setNextPlayer(game.getPlayerX().equals(activePlayer) ? game.getPlayerO() : game.getPlayerX());

        return game;
    }

    @Override
    public Game create(Game game) {
        return gameDAO.save(game);
    }

    @Override
    public boolean deleteById(String uuid) {
        if (!gameDAO.deleteById(uuid)) {
            return false;
        }

        return true;
    }

    public boolean isGameFinished(Game game, Board.FieldSymbol activeSymbol, String activePlayer) {
        HashMap<Board.Field, Board.FieldSymbol> fields = game.getBoard().getFields();

        // Check first if there is a winner
        if (isWinner(fields, activeSymbol)) {
            game.setOutcome(Game.Outcome.WIN);
            game.setWinner(activePlayer);

            game.setNextPlayer(null);
            game.setNextPlayerSymbol(null);
            game.setStatus(Game.Status.FINISHED);

            return true;
        }

        // If no winner, is the game ongoing ? If not, return false;
        if (!fields.containsValue(null)) {

            // If yes, it means that the game is finished. Win or Draw ?
            if (isWinner(fields, activeSymbol)) {
                game.setOutcome(Game.Outcome.WIN);
                game.setWinner(activePlayer);
            } else {
                game.setOutcome(Game.Outcome.DRAW);
            }

            game.setNextPlayer(null);
            game.setNextPlayerSymbol(null);
            game.setStatus(Game.Status.FINISHED);

            return true;
        }

        return false;
    }

    private boolean isWinner(HashMap<Board.Field, Board.FieldSymbol> fields, Board.FieldSymbol activeSymbol) {
        List<Board.Field> activePlayerFields = new ArrayList<>();
        for (Map.Entry<Board.Field, Board.FieldSymbol> field : fields.entrySet()) {
            if (field.getValue() != null && field.getValue().equals(activeSymbol)) {
                activePlayerFields.add(field.getKey());
            }
        }

        for (Board.Field[] winningCombination : WinningConditions) {
            if (activePlayerFields.containsAll(Arrays.asList(winningCombination))) {
                return true;
            }
        }

        return false;
    }
}
