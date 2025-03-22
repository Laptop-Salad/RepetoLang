package com.repeto.lang.ir;

import com.repeto.lang.Token;
import com.repeto.lang.parser.Expr;

import java.util.ArrayList;

public class ASTWalker implements Expr.Visitor<String> {
    public int temporaryNum = 0;
    public StringBuilder ir;
    public ArrayList<String> availableRegisters;

    public ASTWalker() {
        availableRegisters = new ArrayList<>();

        ir = new StringBuilder();
    }

    public String walk(Expr expr) {
        expr.accept(this);

        return ir.toString();
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        String left = expr.left.accept(this);
        String right = expr.right.accept(this);

        String temp = "t" + temporaryNum++;

        ir
            .append(temp)
            .append(" = ")
            .append(left)
            .append(" ")
            .append(expr.operator.getTokenType().toValue())
            .append(" ")
            .append(right)
            .append("\n");

        return temp;
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        String value;
        if (expr.value instanceof Token) {
            value = ((Token) expr.value).getValue();
        } else {
            value = expr.value.toString();
        }

        String temp = "t" + temporaryNum++;

        ir
            .append(temp)
            .append(" = ")
            .append(value)
            .append("\n");

        return temp;
    }
}
