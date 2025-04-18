package com.repeto.lang.library;

import com.repeto.lang.parser.Expr;

public class FunctionHelper {
    public static Expr[] getFullArgs(String functionName, Expr[] args) {
        switch (functionName) {
            case "print":
                return IO.print(args);
        }

        return args;
    }
}
