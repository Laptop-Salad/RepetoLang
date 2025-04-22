//
// Created by Amanda Wallis on 15/04/2025.
//

#include <stdio.h>
#include "io.h"
#include "expr.h"
#include <stdarg.h>
#include <stdlib.h>

void repeat(Expr* expression, int times) {

}

void print(Expr* countExpr, ...) {
    va_list args;
    va_start(args, countExpr);

    LiteralIntExpr* count = (LiteralIntExpr*) countExpr;

    for (int i = 0; i < count->value; i++) {
        Expr* expr = va_arg(args, Expr*);

        switch (expr->type) {
        case TYPE_INT:
            printf("%d", ((LiteralIntExpr*)expr)->value);
            break;
        case TYPE_STRING:
            printf("%s", ((LiteralStringExpr*)expr)->value);
            break;
        default:
            printf("<unknown>");
        }

        printf("\n");
    }

    va_end(args);
}
