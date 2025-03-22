package com.repeto.lang.ir;

import com.repeto.lang.parser.Expr;

public class IRGenerator {
    private Expr expr;

    public IRGenerator(Expr expr) {
        this.expr = expr;
    }

    public String generateIR() {
        StringBuilder ir = new StringBuilder();

        ASTWalker astWalker = new ASTWalker();

        return astWalker.walk(expr);
    }
}
