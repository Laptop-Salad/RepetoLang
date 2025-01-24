package main.java.com.lang.repeto;

import java.util.ArrayList;

public class Lexer {
    private final char[] lines;
    private final ArrayList<Token> tokens;

    private int index = 0;

    public Lexer(String lines) {
        this.lines = lines.toCharArray();
        this.tokens = new ArrayList<>();
    }

    public void lex() {
        for (;;) {
            char character = this.current();

            this.eat(character);

            if (!this.hasNext()) { break; }

            this.next();
        }
    }

    public Token getToken(char character) {
        return new Token(null, null);
    }

    public boolean hasNext() {
        return this.index + 1 < this.lines.length;
    }

    public char peek() {
        return this.lines[this.index + 1];
    }

    public char current() { 
        return this.lines[this.index]; 
    }

    public void next() { this.index++; }

    public void eat(char character) {
        switch (character) {
            case ' ':
            case '\r':
            case '\t':
                break;        

            case '+': addToken(TokenType.PLUS); break;
            case '-': addToken(TokenType.MINUS); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            case '(': addToken(TokenType.OPENING_PARENTHESIS); break;
            case ')': addToken(TokenType.CLOSING_PARENTHESIS); break;

            case '*': 
                addToken(match('*') ? TokenType.POWER_OF : TokenType.TIMES); 
                break;
            case '=': 
                addToken(match('=') ? TokenType.EQUALS_EQUALS : TokenType.EQUALS); 
                break;

            case '/':
                StringBuilder stringBuilder = new StringBuilder();

                if (match('/')) {
                    while (this.hasNext() && peek() != '\n') {
                        stringBuilder.append(character);
                    }

                    addToken(TokenType.COMMENT, stringBuilder.toString());
                } else {
                    addToken(TokenType.DIVIDE);
                }

                break;
            
            case '$': this.handleVariable(); break;

            default:
                if (Character.isDigit(character)) {
                    this.handleDigits();
                } else if (Character.isLetter(character)) {
                    this.handleIdentifier();
                } else {
                    System.out.println("Invalid character");
                }

                break;
        }
    }

    public void handleDigits() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.current());

        while (this.hasNext() && Character.isDigit(this.peek())) {
            this.next();

            stringBuilder.append(this.current());
        }

        addToken(TokenType.INTEGER, stringBuilder.toString());
    }

    public void handleVariable() {
        StringBuilder stringBuilder = new StringBuilder();

        while (
                this.hasNext() &&
                !Character.isWhitespace(this.peek()) &&
                (Character.isAlphabetic(this.peek()) || Character.isDigit(this.peek()) || this.peek() == '_')
        ) {
            this.next();

            stringBuilder.append(this.current());
        }

        addToken(TokenType.VARIABLE, stringBuilder.toString());
    }

    public void handleIdentifier() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.current());

        while (
                this.hasNext() &&
                !Character.isWhitespace(this.peek()) &&
                (Character.isAlphabetic(this.peek()) || Character.isDigit(this.peek()) || this.peek() == '_')
        ) {
            this.next();

            stringBuilder.append(this.current());
        }

        addToken(TokenType.IDENTIFIER, stringBuilder.toString());
    }


    public void addToken(TokenType tokenType) {
        this.tokens.add(new Token(tokenType, null));
    }

    public void addToken(TokenType tokenType, String value) {
        this.tokens.add(new Token(tokenType, value));
    }

    private boolean match(char expected) {
        if (this.hasNext() && this.peek() == expected) { 
            next();
            return true; 
        }

        return false;
    }

    public ArrayList<Token> getTokens() { return this.tokens; }
}
