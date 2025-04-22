package com.repeto.lang;

import com.repeto.lang.codegenerator.CodeGenerator;
import com.repeto.lang.lexer.Lexer;
import com.repeto.lang.parser.Expr;
import com.repeto.lang.parser.Parser;
import com.repeto.lang.semanticanalysis.SemanticAnalyser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") + "/examples/Year.repeto");
        File compiled = new File(System.getProperty("user.dir") + "/compiled/Year.c");
        StringBuilder lines = new StringBuilder();

        Lexer lexer;
        Parser parser;
        SemanticAnalyser semanticAnalyser;

        CodeGenerator codeGenerator;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                lines.append(sc.nextLine());
            }
            sc.close();

            // generate tokens
            lexer = new Lexer(lines.toString());
            lexer.lex();

            // generate ast
            parser = new Parser(lexer.getTokens());
            List<Expr> expressions = parser.parse();

            // semantic analysis
            semanticAnalyser = new SemanticAnalyser();
            expressions = semanticAnalyser.analyse(expressions);

            // generate c code
            codeGenerator = new CodeGenerator(expressions);
            String code = codeGenerator.generate();

            // write compiled to file
            FileWriter writer = new FileWriter(compiled);
            writer.write(code);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + file.getAbsolutePath());
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lexer = new Lexer(lines.toString());
        lexer.lex();

        parser = new Parser(lexer.getTokens());
        parser.parse();
    }
}
