package main.java.com.lang.repeto.parser;

import main.java.com.lang.repeto.Token;
import main.java.com.lang.repeto.TokenType;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;
    private int currentIndex = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public Expr parse() {
        return expression();
    }

    private Expr expression() {
        return term();
    }

    private Expr term() {
        Expr expr = factor();

        while (match(TokenType.MINUS, TokenType.PLUS)) {
            Token operator = current();
            advance();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr factor() {
        Expr expr = primary();

        while (match(TokenType.TIMES, TokenType.DIVIDE)) {
            Token operator = current();
            advance();
            Expr right = primary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr primary() {
        return new Expr.Literal(current().getValue());
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) { return false; }

        return peek().getTokenType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) { currentIndex++; }

        return previous();
    }

    private boolean isAtEnd() {
        return currentIndex == this.tokens.size();
    }

    private Token peek() {
        return this.tokens.get(currentIndex + 1);
    }

    public Token current() {
        return this.tokens.get(currentIndex);
    }

    private Token previous() {
        return this.tokens.get(currentIndex - 1);
    }
}
