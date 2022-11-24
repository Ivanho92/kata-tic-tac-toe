package com.ivan_rodrigues.kata_tic_tac_toe.model;

public class NewGame {
    private String playerX;
    private String playerO;

    public NewGame(String playerX, String playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
    }

    public String getPlayerX() {
        return playerX;
    }

    public String getPlayerO() {
        return playerO;
    }
}
