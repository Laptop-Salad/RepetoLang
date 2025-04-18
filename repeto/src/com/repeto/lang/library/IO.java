package com.repeto.lang.library;

import com.repeto.lang.parser.Expr;

public class IO implements Function {
    public static Expr[] print(Expr[] output) {
        Expr[] fullArgs = new Expr[output.length + 1];

        // Add count
        fullArgs[0] = new Expr.Literal(output.length);

        for (int i = 1; i <= output.length; i++) {
            Expr expr = output[i - 1];
            fullArgs[i] = expr;
        }

        return fullArgs;
    }
}
