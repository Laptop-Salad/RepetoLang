package com.repeto.lang.parser;

import com.repeto.lang.lexer.Token;
import com.repeto.lang.library.FunctionHelper;
import com.repeto.lang.semanticanalysis.Type;

import java.util.Arrays;

public abstract class Expr {
    public Type inferredType;

    public interface Visitor<R> {
        R visitBinaryExpr(Binary expr);
        R visitLiteralExpr(Literal expr);
        R visitCallExpr(FunctionCall expr);
    }

    public static class Binary extends Expr {
        public final Expr left;
        public final Token operator;
        public final Expr right;

        public Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }
    }

    public static class Literal extends Expr {
        public final Object value;

        public Literal(Object value) {
//            System.out.println(value + "=" + value.getClass().getSimpleName());
            this.value = value;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }
    }

    public static class FunctionCall extends Expr {
        public final String name;
        public final Expr[] arguments;

        public FunctionCall(String name, Expr[] arguments) {
            this.name = name;
            this.arguments = FunctionHelper.getFullArgs(this.name, arguments);
        }

        @Override
        public <R> R accept(Visitor<R> visitor) { return visitor.visitCallExpr(this); }
    }

    public abstract <R> R accept(Visitor<R> visitor);
}
