package com.repeto.lang.parser;

import com.repeto.lang.lexer.Token;
import com.repeto.lang.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private ArrayList<Token> tokens;
    private int currentIndex = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Expr> parse() {
        List<Expr> exprs = new ArrayList<Expr>();

        while (!isAtEnd()) {
            exprs.add(expression());
            advance();
        }

        return exprs;
    }

    private Expr expression() {
        return term();
    }

    private Expr term() {
        Expr expr = factor();

        if (match(TokenType.MINUS, TokenType.PLUS)) {
            Token operator = current();
            advance();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr factor() {
        Expr expr = primary();

        if (match(TokenType.TIMES, TokenType.DIVIDE)) {
            Token operator = current();
            advance();
            Expr right = primary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr primary() {
        if (
            current().getTokenType() == TokenType.IDENTIFIER &&
            peek().getTokenType() == TokenType.OPENING_PARENTHESIS
        ) {
            return function();
        }

        return new Expr.Literal(current().getValue());
    }

    private Expr function() {
        ArrayList<Expr> args = new ArrayList<>();
        String functionName = (String) current().getValue();

        advance(); // past function name

        if (current().getTokenType() != TokenType.OPENING_PARENTHESIS) {
            throw new RuntimeException("Expected '(' after function name");
        }

        advance(); // past opening parenthesis

        if (current().getTokenType() != TokenType.CLOSING_PARENTHESIS) {
            do {
                args.add(expression());
            } while (match(TokenType.COMMA));
        }

        advance(); // consume ')'

        if (current().getTokenType() != TokenType.CLOSING_PARENTHESIS) {
            throw new RuntimeException("Expected ')' after arguments");
        }

        Expr[] argumentArr = args.toArray(new Expr[0]);

        return new Expr.FunctionCall(functionName, argumentArr);
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
        return currentIndex == this.tokens.size() - 1;
    }

    private Token peek() {
        if (isAtEnd()) { return null; }

        return this.tokens.get(currentIndex + 1);
    }

    public Token current() {
        return this.tokens.get(currentIndex);
    }

    private Token previous() {
        return this.tokens.get(currentIndex - 1);
    }
}
