package tests.LexerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.java.com.lang.repeto.Lexer;
import main.java.com.lang.repeto.Token;
import main.java.com.lang.repeto.TokenType;

public class CompleteTest {
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

    @Test
    public void canTokenizeAssignVar() {
        Lexer lexer = new Lexer("$my_age = 2024 - 2000;");
        lexer.lex();

        ArrayList<Token> tokens = lexer.getTokens();

        // my_age, identifier
        Token token = tokens.get(0);
        assertEquals("my_age",token.getValue());
        assertEquals(TokenType.IDENTIFIER, token.getTokenType());

        // null, EQUALS
        token = tokens.get(1);
        assertNull(token.getValue());
        assertEquals(TokenType.EQUALS, token.getTokenType());

        // 2024, Integer
        token = tokens.get(2);
        assertEquals("2024",token.getValue());
        assertEquals(TokenType.INTEGER, token.getTokenType());

        // null, Minus
        token = tokens.get(3);
        assertNull(token.getValue());
        assertEquals(TokenType.MINUS, token.getTokenType());

        // 2000, Integer
        token = tokens.get(4);
        assertEquals("2000", token.getValue());
        assertEquals(TokenType.INTEGER, token.getTokenType());

        // null, Semicolon
        token = tokens.get(5);
        assertNull(token.getValue());
        assertEquals(TokenType.SEMICOLON, token.getTokenType());        

        assertEquals(6, tokens.size());
    }
}
