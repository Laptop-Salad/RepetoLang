package com.repeto.lang.codegenerator;

import com.repeto.lang.parser.Expr;
import com.repeto.lang.semanticanalysis.Type;

public class ASTWalker implements Expr.Visitor<String> {
    public String walk(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.inferredType == Type.INTEGER) {
            return "createLiteralInt(" + expr.value + ")";
        } else if (expr.inferredType == Type.STRING) {
            return "createLiteralString(\"" + expr.value + "\")";
        }

        // No other possible types so...
        throw new IllegalStateException("Invalid type");
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        String left = String.format("((LiteralIntExpr*)%s)->value", expr.left.accept(this));
        String right = String.format("((LiteralIntExpr*)%s)->value", expr.right.accept(this));

        return String.format(
                "createLiteralInt(%s %s %s)",
                left,
                expr.operator.getTokenType().toValue(),
                right
        );
    }

    @Override
    public String visitCallExpr(Expr.FunctionCall expr) {
        StringBuilder arguments = new StringBuilder();

        for (int i = 0; i < expr.arguments.length; i++) {
            arguments.append(expr.arguments[i].accept(this));

            if (i < expr.arguments.length - 1) {
                arguments.append(", ");
            }
        }

        return expr.name + "(" + arguments + ");";
    }
}
