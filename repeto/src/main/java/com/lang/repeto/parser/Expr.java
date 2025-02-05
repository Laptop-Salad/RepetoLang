package main.java.com.lang.repeto.parser;

import main.java.com.lang.repeto.Token;

public abstract class Expr {
    public interface Visitor<R> {
        R visitBinaryExpr(Binary expr);
        R visitLiteralExpr(Literal expr);
    }

    public static class Binary extends Expr {
        public final Token operator;
        public final Expr left;
        public final Expr right;

        public Binary(Token operator, Expr left, Expr right) {
            this.operator = operator;
            this.left = left;
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
            this.value = value;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }
    }


    public abstract <R> R accept(Visitor<R> visitor);
}
