package com.repeto.lang.lexer;

public enum TokenType {
    VARIABLE,
    IDENTIFIER,
    FUNCTION,

    PLUS,
    MINUS,
    DIVIDE,
    TIMES,
    POWER_OF,

    EQUALS,

    EQUALS_EQUALS,
    
    INTEGER,
    STRING,
    
    SEMICOLON,
    
    COMMENT,
    COMMA,

    OPENING_PARENTHESIS,
    CLOSING_PARENTHESIS,

    EOF;

    public String toValue() {
        return switch (this) {
            case
                VARIABLE,
                INTEGER,
                IDENTIFIER,
                FUNCTION,
                STRING
                -> null;
            case PLUS -> "+";
            case MINUS -> "-";
            case DIVIDE -> "/";
            case TIMES -> "*";
            case POWER_OF -> "**";
            case EQUALS -> "=";
            case EQUALS_EQUALS -> "==";
            case SEMICOLON -> ";";
            case COMMENT -> "//";
            case COMMA -> ",";
            case OPENING_PARENTHESIS -> "(";
            case CLOSING_PARENTHESIS -> ")";
            case EOF -> "EOF";
        };
    }
}
