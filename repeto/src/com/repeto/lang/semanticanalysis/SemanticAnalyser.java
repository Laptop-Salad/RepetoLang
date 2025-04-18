package com.repeto.lang.semanticanalysis;

import com.repeto.lang.parser.Expr;

public class SemanticAnalyser implements Expr.Visitor<Expr> {
    public Expr analyse(Expr expr) {
        return expr;
    }

    @Override
    public Expr visitBinaryExpr(Expr.Binary expr) {
        analyse(expr.left);
        analyse(expr.right);

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
        if (expr.value instanceof Integer) {
            expr.inferredType = Type.INTEGER;
        } else if (expr.value instanceof String) {
            expr.inferredType = Type.STRING;
        }

        return expr;
    }

    @Override
    public Expr visitCallExpr(Expr.FunctionCall expr) {
        expr.inferredType = Type.NULL;

        return expr;
    }
}
