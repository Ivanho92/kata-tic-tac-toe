package com.ivan_rodrigues.kata_tic_tac_toe.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class Game {

    public enum Status {
        NEW,
        ONGOING,
        FINISHED
    }

    private UUID uuid;
    private long createdOn;
    private long updatedOn;

    private String playerX;
    private String playerY;

    private Status status;
    private String nextPlayer;
    private Board.FieldSymbol nextPlayerSymbol;

    @Autowired
    private Board board;

    public Game(String playerX, String playerY) {
        this.uuid = UUID.randomUUID();
        this.createdOn = System.currentTimeMillis();
        this.updatedOn = System.currentTimeMillis();
        this.playerX = playerX;
        this.playerY = playerY;
        this.status = Status.NEW;
        this.nextPlayer = playerX;
        this.nextPlayerSymbol = Board.FieldSymbol.X;

        this.board = new Board();
    }

    public Game(String uuid, String playerX, String playerY) {
        this(playerX, playerY);
        this.uuid = UUID.fromString(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getCreatedOn() {
        return createdOn;
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

    public String getPlayerY() {
        return playerY;
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

    public Board getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "Game{" +
                "uuid=" + uuid +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", playerX='" + playerX + '\'' +
                ", playerY='" + playerY + '\'' +
                ", status='" + status + '\'' +
                ", nextPlayer='" + nextPlayer + '\'' +
                ", nextPlayerSymbol='" + nextPlayerSymbol + '\'' +
                ", board=" + board +
                '}';
    }
}
