package main.java.com.lang.repeto;

import java.util.ArrayList;
import java.util.function.Predicate;

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

        this.addToken(TokenType.EOF);
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
            case '\n':
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
                    throw new IllegalArgumentException("Invalid character encountered: " + character);
                }

                break;
        }
    }

    private String consumeWhile(Predicate<Character> condition) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.current());

        while (this.hasNext() && condition.test(this.peek())) {
            this.next();
            stringBuilder.append(this.current());
        }

        return stringBuilder.toString();
    }

    public void handleDigits() {
        String value = consumeWhile(Character::isDigit);

        addToken(TokenType.INTEGER, value);
    }

    public void handleVariable() {
        // skip $
        this.next();

        String value = consumeWhile(ch ->
            Character.isAlphabetic(ch) ||
            Character.isDigit(ch) ||
            ch == '_'
        );

        addToken(TokenType.VARIABLE, value);
    }

    public void handleIdentifier() {
        String value = consumeWhile(ch ->
            !Character.isWhitespace(ch) &&
            (Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '_')
        );

        addToken(TokenType.IDENTIFIER, value);
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
