package main.java.com.lang.repeto;

public class Token {
    private final String value;
    private final TokenType tokenType;

    public Token(TokenType tokenType, String value) {
        System.out.print(value);
        System.out.print(" = ");
        System.out.print(tokenType);
        System.out.print("\n");

        this.value = value;
        this.tokenType = tokenType;
    }

    public String getValue() { return this.value; }
    public TokenType getTokenType() { return this.tokenType; }
}