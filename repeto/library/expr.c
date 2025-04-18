//
// Created by Amanda Wallis on 18/04/2025.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "expr.h"

const char* typeToString(Type type) {
  switch (type) {
    case TYPE_INT: return "int";
    case TYPE_STRING: return "string";
    default: return "unknown";
  }
}

const char typeToFormat(Type type) {
  switch (type) {
    case TYPE_INT: return 'd';
    case TYPE_STRING: return 's';
    default: return 'n';
  }
}

Expr* createLiteralInt(int value) {
  LiteralIntExpr* expr = (LiteralIntExpr*)malloc(sizeof(LiteralIntExpr));
  expr->base.type = TYPE_INT;
  expr->value = value;
  return (Expr*) expr;
}

Expr* createLiteralString(char* value) {
  LiteralStringExpr* expr = (LiteralStringExpr*) malloc(sizeof(LiteralStringExpr));
  expr->base.type = TYPE_STRING;
  expr->value = strdup(value);
  return (Expr*) expr;
}

Expr* createBinary(char operator, Expr* left, Expr* right, Type type) {
  BinaryExpr* expr = (BinaryExpr*) malloc(sizeof(BinaryExpr));
  expr->base.type = type;
  expr->left = left;
  expr->right = right;
  expr->operator = operator;
  return (Expr*) expr;
}

Expr* createFunctionCall(char* fnName, Expr** args, int argCount, Type type) {
  FunctionCallExpr* expr = (FunctionCallExpr*) malloc(sizeof(FunctionCallExpr));
  expr->base.type = type;
  expr->argCount = argCount;
  expr->args = args;
  expr->fnName = fnName;

  return (Expr*) expr;
}