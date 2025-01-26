package main.java.com.lang.repeto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") + "/examples/MyAge.repeto");
        StringBuilder lines = new StringBuilder();
        Lexer lexer;
        Parser parser;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                lines.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + file.getAbsolutePath());
            System.exit(0);
        }

        lexer = new Lexer(lines.toString());
        lexer.lex();

        parser = new Parser(lexer.getTokens());
        parser.parse();
    }
}
