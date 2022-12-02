package com.ivan_rodrigues.kata.tictactoe.model.data;

import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardField;
import com.ivan_rodrigues.kata.tictactoe.model.data.enums.BoardFieldSymbol;

import java.util.HashMap;

public class Board {
    // Properties
    private HashMap<BoardField, BoardFieldSymbol> fields;

    // Constructors
    public Board() {
        fields = new HashMap<>();
        fields.put(BoardField.A1, null);
        fields.put(BoardField.A2, null);
        fields.put(BoardField.A3, null);
        fields.put(BoardField.B1, null);
        fields.put(BoardField.B2, null);
        fields.put(BoardField.B3, null);
        fields.put(BoardField.C1, null);
        fields.put(BoardField.C2, null);
        fields.put(BoardField.C3, null);
    }

    public Board(HashMap<BoardField, BoardFieldSymbol> fields) {
        this.fields = fields;
    }

    // Getter
    public HashMap<BoardField, BoardFieldSymbol> getFields() {
        return fields;
    }
}
