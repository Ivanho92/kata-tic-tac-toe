package com.ivan_rodrigues.kata_tic_tac_toe.model.data;

import java.util.*;

public class Game {
    // Enumerations
    public enum Status {NEW, ONGOING, FINISHED}
    public enum Outcome {WIN, DRAW}

    // Properties
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
