package com.lang.repeto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class LexerTest {

    @Test
    public void canTokenize5Add5() {
        Lexer lexer = new Lexer("5 + 5");
        lexer.lex();

        ArrayList<Token> tokens = lexer.getTokens();

        // 5, Integer
        Token token = tokens.get(0);
        assertEquals("5",token.getValue());
        assertEquals(TokenType.INTEGER, token.getTokenType());

        // null, Plus
        token = tokens.get(1);
        assertNull(token.getValue());
        assertEquals(TokenType.PLUS, token.getTokenType());

        // 5, Integer
        token = tokens.get(2);
        assertEquals("5",token.getValue());
        assertEquals(TokenType.INTEGER, token.getTokenType());

        assertEquals(3, tokens.size());
    }
}
