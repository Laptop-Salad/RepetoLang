package com.repeto.lang.lexer;

public enum TokenType {
    VARIABLE,
    IDENTIFIER,

    PLUS,
    MINUS,
    DIVIDE,
    TIMES,
    POWER_OF,

    EQUALS,

    EQUALS_EQUALS,
    
    INTEGER,
    
    SEMICOLON,
    
    COMMENT,

    OPENING_PARENTHESIS,
    CLOSING_PARENTHESIS,

    EOF;

    public String toValue() {
        return switch (this) {
            case
                VARIABLE,
                INTEGER,
                IDENTIFIER -> null;
            case PLUS -> "+";
            case MINUS -> "-";
            case DIVIDE -> "/";
            case TIMES -> "*";
            case POWER_OF -> "**";
            case EQUALS -> "=";
            case EQUALS_EQUALS -> "==";
            case SEMICOLON -> ";";
            case COMMENT -> "//";
            case OPENING_PARENTHESIS -> "(";
            case CLOSING_PARENTHESIS -> ")";
            case EOF -> "EOF";
        };
    }
}
