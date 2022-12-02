package com.ivan_rodrigues.kata.tictactoe.model.data;

import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardFieldSymbol;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.GameOutcome;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.GameStatus;

import java.util.UUID;

public class Game {
    // Properties
    private UUID uuid;
    private long createdOn;
    private long updatedOn;
    private String playerX;
    private String playerO;
    private String winner;
    private GameStatus status;
    private String nextPlayer;
    private BoardFieldSymbol nextPlayerSymbol;
    private GameOutcome outcome;
    private Board board;

    // Constructors
    public Game(String playerX, String playerO) {
        this.uuid = UUID.randomUUID();
        this.createdOn = System.currentTimeMillis();
        this.updatedOn = System.currentTimeMillis();
        this.playerX = playerX;
        this.playerO = playerO;
        this.winner = null;
        this.status = GameStatus.NEW;
        this.nextPlayer = playerX;
        this.nextPlayerSymbol = BoardFieldSymbol.X;
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

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public BoardFieldSymbol getNextPlayerSymbol() {
        return nextPlayerSymbol;
    }

    public void setNextPlayerSymbol(BoardFieldSymbol nextPlayerSymbol) {
        this.nextPlayerSymbol = nextPlayerSymbol;
    }

    public GameOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(GameOutcome outcome) {
        this.outcome = outcome;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Game{" +
                "uuid=" + uuid +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", playerX='" + playerX + '\'' +
                ", playerO='" + playerO + '\'' +
                ", winner='" + winner + '\'' +
                ", status=" + status +
                ", nextPlayer='" + nextPlayer + '\'' +
                ", nextPlayerSymbol=" + nextPlayerSymbol +
                ", outcome=" + outcome +
                ", board=" + board +
                '}';
    }
}
