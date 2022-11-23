package com.ivan_rodrigues.kata_tic_tac_toe.model;

public class PlayMove {
    private String player;
    private Board.Field field;

    public PlayMove(String player, Board.Field field) {
        this.player = player;
        this.field = field;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Board.Field getField() {
        return field;
    }

    public void setField(Board.Field field) {
        this.field = field;
    }
}
