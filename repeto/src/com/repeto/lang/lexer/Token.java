package com.repeto.lang.lexer;

public class Token {
    private final Object value;
    private final TokenType tokenType;

    public Token(TokenType tokenType, Object value) {
        this.value = value;
        this.tokenType = tokenType;
    }

    public Object getValue() { return this.value; }
    public TokenType getTokenType() { return this.tokenType; }
}