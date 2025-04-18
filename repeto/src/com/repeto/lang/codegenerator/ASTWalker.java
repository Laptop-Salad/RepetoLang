package com.repeto.lang.codegenerator;

import com.repeto.lang.parser.Expr;

public class ASTWalker implements Expr.Visitor<String> {
    public String walk(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        return "createLiteralInt(" + expr.value.toString() + ")";
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
