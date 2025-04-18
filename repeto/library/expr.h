//
// Created by Amanda Wallis on 18/04/2025.
//

#ifndef EXPR_H
#define EXPR_H

typedef enum {
  TYPE_INT,
  TYPE_STRING,
} Type;

typedef struct {
  Type type;
} Expr;

typedef struct {
  Expr base;
  int value;
} LiteralIntExpr;

typedef struct {
  Expr base;
  char* value;
} LiteralStringExpr;

typedef struct {
  Expr base;
  char operator;
  Expr* left;
  Expr* right;
} BinaryExpr;

typedef struct {
  Expr base;
  char* fnName;
  Expr** args;
  int argCount;
} FunctionCallExpr;

// Function prototypes for creating expressions
Expr* createLiteralInt(int value);
Expr* createLiteralString(char* value);
Expr* createBinary(char operator, Expr* left, Expr* right, Type type);
Expr* createFunctionCall(char* fnName, Expr** args, int argCount, Type type);
const char* typeToString(Type type);
const char typeToFormat(Type type);

#endif // EXPR_H