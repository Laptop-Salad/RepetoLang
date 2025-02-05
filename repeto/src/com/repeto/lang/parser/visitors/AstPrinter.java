package com.repeto.lang.parser.visitors;

import com.repeto.lang.Token;
import com.repeto.lang.parser.Expr;

public class AstPrinter implements Expr.Visitor<String> {
    public String print(Expr expr) { return expr.accept(this); }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.getTokenType().toString(), expr.left, expr.right);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value instanceof Token) {
            return ((Token) expr.value).getValue();
        }

        return parenthesize(expr.value.toString());
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder sb = new StringBuilder();

        sb.append("(").append(name);

        for (Expr expr : exprs) {
            sb.append(" ");
            sb.append(expr.accept(this));
        }

        sb.append(")");

        return sb.toString();
    }
}
