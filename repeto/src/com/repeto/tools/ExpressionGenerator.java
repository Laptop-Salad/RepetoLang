package com.repeto.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * java repeto/src/main/java/com/lang/tools/ExpressionGenerator.java repeto/src/main/java/com/lang/repeto/parser
 */
public class ExpressionGenerator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: generate_expression <output dir>");
            System.exit(1);
        }

        String outputDir = args[0];

        try {
            defineAST(outputDir, "Expr", Arrays.asList(
                    "Binary: Expr left, Token operator, Expr right",
                    "Literal: Object value"
            ));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void defineAST(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package main.java.com.lang.repeto.parser;");
        writer.println();
        writer.println("import main.java.com.lang.repeto.Token;");
        writer.println();
        writer.println("public abstract class " + baseName + " {");

        defineVisitor(writer, baseName, types);

        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        }

        // accept method for visitor pattern
        writer.println();
        writer.println("    public abstract <R> R accept(Visitor<R> visitor);");

        writer.println("}");
        writer.close();
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fields) throws IOException {
        writer.println("    public static class " + className + " extends " + baseName + " {");

        String[] fieldsArr = Arrays.stream(fields.split(",")).map(String::trim).toArray(String[]::new);

        // Fields
        for (String field : fieldsArr) {
            writer.println("        public final " + field + ";");
        }

        writer.println("");

        writer.println("        public " + className + "(" + fields + ") {");

        // Store params in fields
        for (String field : fieldsArr) {
            String name = field.split(" ")[1];

            writer.println("            this." + name + " = " + name + ";");
        }

        writer.println("        }");

        // accept() for visitor pattern
        writer.println();

        writer.println("        @Override");
        writer.println("        public <R> R accept(Visitor<R> visitor) {");
        writer.println("            return visitor.visit" + className + baseName + "(this);");
        writer.println("        }");

        writer.println("    }");

        writer.println();
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) throws IOException {
        writer.println("    public interface Visitor<R> {");

        for (String type : types) {
            String name = type.split(":")[0].trim();

            writer.println("        R visit" + name + baseName + "(" + name + " " + baseName.toLowerCase() + ");");
        }

        writer.println("    }");
        writer.println();
    }
}
