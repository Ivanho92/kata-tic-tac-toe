package com.ivan_rodrigues.kata_tic_tac_toe.model;

import java.util.HashMap;

public class Board {

    public enum Field {
        A1,
        A2,
        A3,
        B1,
        B2,
        B3,
        C1,
        C2,
        C3
    }

    public enum FieldSymbol {
        X,
        O
    }

    private HashMap<Field, FieldSymbol> fields;

    public Board() {
        fields = new HashMap<>();

        fields.put(Field.A1, null);
        fields.put(Field.A2, null);
        fields.put(Field.A3, null);
        fields.put(Field.B1, null);
        fields.put(Field.B2, null);
        fields.put(Field.B3, null);
        fields.put(Field.C1, null);
        fields.put(Field.C2, null);
        fields.put(Field.C3, null);
    }

    public Board(HashMap<Board.Field, Board.FieldSymbol> fields) {
        this.fields = fields;
    }

    public HashMap<Field, FieldSymbol> getFields() {
        return fields;
    }
}
