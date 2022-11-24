package com.ivan_rodrigues.kata_tic_tac_toe.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Game {
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

    public enum Status {NEW, ONGOING, FINISHED}

    public enum Outcome {WIN, DRAW}

    private UUID uuid;
    private long createdOn;
    private long updatedOn;
    private String playerX;
    private String playerO;
    private String winner;
    private Status status;
    private String nextPlayer;
    private Board.FieldSymbol nextPlayerSymbol;
    private Outcome outcome;

    @Autowired
    private Board board;

    // Constructors
    public Game(String playerX, String playerO) {
        this.uuid = UUID.randomUUID();
        this.createdOn = System.currentTimeMillis();
        this.updatedOn = System.currentTimeMillis();

        this.playerX = playerX;
        this.playerO = playerO;
        this.winner = null;

        this.status = Status.NEW;
        this.nextPlayer = playerX;
        this.nextPlayerSymbol = Board.FieldSymbol.X;

        this.outcome = null;

        this.board = new Board();
    }

    public Game(String playerX, String playerO, Board board) {
        this(playerX, playerO);
        this.board = board;
    }

    // Logic methods
    public boolean isGameFinished(Game game, Board.FieldSymbol activeSymbol) {
        HashMap<Board.Field, Board.FieldSymbol> fields = game.getBoard().getFields();

        // Check first if there is a winner
        if (isWinner(fields, activeSymbol)) {
            this.setOutcome(Outcome.WIN);

            this.setNextPlayer(null);
            this.setNextPlayerSymbol(null);
            this.setStatus(Status.FINISHED);

            return true;
        }

        // If not, is the game ongoing ? If not, return false;
        if (!fields.containsValue(null)) {

            // If yes, it means that the game is finished. Win or Draw ?
            if (isWinner(fields, activeSymbol)) {
                this.setOutcome(Outcome.WIN);
            } else {
                this.setOutcome(Outcome.DRAW);
            }

            this.setNextPlayer(null);
            this.setNextPlayerSymbol(null);
            this.setStatus(Status.FINISHED);

            return true;
        }

        return false;
    }

    public boolean isWinner(HashMap<Board.Field, Board.FieldSymbol> fields, Board.FieldSymbol activeSymbol) {
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

    // Getters and setters
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getPlayerX() {
        return playerX;
    }

    public void setPlayerX(String playerX) {
        this.playerX = playerX;
    }

    public String getPlayerO() {
        return playerO;
    }

    public void setPlayerO(String playerO) {
        this.playerO = playerO;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public Board.FieldSymbol getNextPlayerSymbol() {
        return nextPlayerSymbol;
    }

    public void setNextPlayerSymbol(Board.FieldSymbol nextPlayerSymbol) {
        this.nextPlayerSymbol = nextPlayerSymbol;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
