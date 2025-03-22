package com.repeto.lang;

import com.repeto.lang.code_generator.CodeGenerator;
import com.repeto.lang.ir.IRGenerator;
import com.repeto.lang.lexer.Lexer;
import com.repeto.lang.parser.Expr;
import com.repeto.lang.parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") + "/examples/Year.repeto");
        File compiled = new File(System.getProperty("user.dir") + "/compiled/Year.s");
        StringBuilder lines = new StringBuilder();

        Lexer lexer;
        Parser parser;
        IRGenerator irGenerator;
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
            Expr expression = parser.parse();

            // generate ir
            irGenerator = new IRGenerator(expression);
            String ir = irGenerator.generateIR();

            // generate assembly
            codeGenerator = new CodeGenerator(ir);
            String code = codeGenerator.generateCode();

            // write compiled to file
            FileWriter writer = new FileWriter(compiled);
            writer.write(code);
            writer.close();

            // create object file from
            Runtime rt = Runtime.getRuntime();
            rt.exec("clang " + System.getProperty("user.dir") + "/compiled/Year.s" + " -o " + System.getProperty("user.dir") + "/executables/Year");

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
