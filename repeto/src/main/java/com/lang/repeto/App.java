package main.java.com.lang.repeto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        File file;
        StringBuilder lines = new StringBuilder();
        Scanner sc;
        Lexer lexer;
        Parser parser;

        try {
            file = new File(System.getProperty("user.dir") + "/examples/MyAge.repeto");

            sc = new Scanner(file);

            while (sc.hasNextLine()) {
                lines.append(sc.nextLine());
            }

            sc.close();

            lexer = new Lexer(lines.toString());
            lexer.lex();

            parser = new Parser(lexer.getTokens());
            parser.parse();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(0);
        }
    }
}
