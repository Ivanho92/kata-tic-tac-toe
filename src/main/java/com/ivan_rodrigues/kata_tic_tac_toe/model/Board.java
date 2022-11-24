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

        fields.put(Field.A1, FieldSymbol.X);
        fields.put(Field.A2, FieldSymbol.X);
        fields.put(Field.A3, null);
        fields.put(Field.B1, FieldSymbol.O);
        fields.put(Field.B2, FieldSymbol.O);
        fields.put(Field.B3, null);
        fields.put(Field.C1, FieldSymbol.X);
        fields.put(Field.C2, FieldSymbol.O);
        fields.put(Field.C3, FieldSymbol.O);
    }

    public HashMap<Field, FieldSymbol> getFields() {
        return fields;
    }
}
