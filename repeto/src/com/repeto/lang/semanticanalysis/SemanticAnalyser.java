package com.repeto.lang.semanticanalysis;

import com.repeto.lang.parser.Expr;

import java.util.List;

public class SemanticAnalyser implements Expr.Visitor<Expr> {
    public List<Expr> analyse(List<Expr> expr) {
        for (Expr e : expr) {
            e.accept(this);
        };

        return expr;
    }

    @Override
    public Expr visitBinaryExpr(Expr.Binary expr) {
        expr.left.accept(this);
        expr.right.accept(this);

        // Check if the left and right operands have compatible types
        if (expr.left.inferredType != expr.right.inferredType) {
            throw new RuntimeException(
                    "Operands must have the same type for binary operations with operator: "
                    + expr.operator.getTokenType()
            );
        }

        // Since both left and right have the same type we can just set this op to either
        expr.inferredType = expr.left.inferredType;

        return expr;
    }

    @Override
    public Expr visitLiteralExpr(Expr.Literal expr) {
        switch (expr.value.getClass().getSimpleName()) {
            case "String":
                expr.inferredType = Type.STRING;
                break;
            case "Integer":
                expr.inferredType = Type.INTEGER;
                break;
            default:
                // No other possible types so...
                throw new IllegalStateException("Invalid type");
        }

        return expr;
    }

    @Override
    public Expr visitCallExpr(Expr.FunctionCall expr) {
        expr.inferredType = Type.NULL;

        for (Expr arg : expr.arguments) {
            arg.accept(this);
        }

        return expr;
    }
}
