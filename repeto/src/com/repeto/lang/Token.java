package com.repeto.lang;

public class Token {
    private final String value;
    private final TokenType tokenType;

    public Token(TokenType tokenType, String value) {
        this.value = value;
        this.tokenType = tokenType;
    }

    public String getValue() { return this.value; }
    public TokenType getTokenType() { return this.tokenType; }
}