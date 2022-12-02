package com.ivan_rodrigues.kata.tictactoe.model.request;

import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardField;

import javax.validation.constraints.NotBlank;

public class PlayMove {

    @NotBlank
    private String player;

    @NotBlank
    private BoardField field;

    public PlayMove(String player, BoardField field) {
        this.player = player;
        this.field = field;
    }

    public String getPlayer() {
        return player;
    }

    public BoardField getField() {
        return field;
    }
}
