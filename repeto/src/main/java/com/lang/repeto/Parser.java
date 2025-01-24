package main.java.com.lang.repeto;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        System.out.println("parsing...");
    }
}
