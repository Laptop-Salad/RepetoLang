package com.repeto.lang.code_generator;

import java.util.Arrays;

public class CodeGenerator {
    String[] lines;

    public CodeGenerator(String ir) {
       lines = ir.split("\n");
    }

    public String generateCode() {
        StringBuilder code = new StringBuilder();

        code.append(".global _main\n\n");
        code.append("_main:\n");

        for (String line : lines) {
            String[] words = line.replace("=", "").split(" ");
            words = Arrays.stream(words)
                    .filter(word -> !word.isEmpty())
                    .toArray(String[]::new);

            // Assignment
            if (words.length == 2) {
                code
                    .append("\tMOV W")
                    .append(words[0].replace("t", ""))
                    .append(", ")
                    .append(words[1])
                    .append("\n");
            }

            // Operation
            if (words.length == 4) {
                code
                    .append("\t")
                    .append(getASMOp(words[2]))
                    .append(" W")
                    .append(words[0].replace("t", ""))
                    .append(", W")
                    .append(words[1].replace("t", ""))
                    .append(", W")
                    .append(words[3].replace("t", ""))
                ;
            }
        }

        code.append("\n \tret\n");

        return code.toString();
    }

    public String getASMOp(String op) {
        return switch (op) {
            case "-" -> "SUB";
            case "+" -> "ADD";
            case "*" -> "MUL";
            case "/" -> "DIV";
            default -> throw new IllegalStateException("Unexpected value: " + op);
        };
    }
}
