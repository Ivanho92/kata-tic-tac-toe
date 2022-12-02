package com.ivan_rodrigues.kata.tictactoe.model.request;

import javax.validation.constraints.NotBlank;

public class NewGame {

    @NotBlank
    private String playerX;

    @NotBlank
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
