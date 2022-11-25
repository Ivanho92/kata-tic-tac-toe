package com.ivan_rodrigues.kata_tic_tac_toe.model.request;

import com.ivan_rodrigues.kata_tic_tac_toe.model.data.Board;

import javax.validation.constraints.NotBlank;

public class PlayMove {

    @NotBlank
    private String player;

    @NotBlank
    private Board.Field field;

    public PlayMove(String player, Board.Field field) {
        this.player = player;
        this.field = field;
    }

    public String getPlayer() {
        return player;
    }

    public Board.Field getField() {
        return field;
    }
}
