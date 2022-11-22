package com.ivan_rodrigues.kata_tic_tac_toe.model;

import java.util.UUID;

public class Game {

    private UUID uuid;
    private long createdOn;
    private long updatedOn;

    private String playerX;
    private String playerY;

    private String status;
    private String nextPlayer;

    private Board board;

    public Game(String playerX, String playerY) {
        this.uuid = UUID.randomUUID();
        this.createdOn = System.currentTimeMillis();
        this.updatedOn = System.currentTimeMillis();
        this.playerX = playerX;
        this.playerY = playerY;
        this.status = "new";
        this.nextPlayer = "X";

        this.board = new Board();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public Board getBoard() {
        return board;
    }
}
