# Grammar

```
expression -> term ;
term -> factor ( ( "-" | "+" ) factor )* ;
factor -> primary ( ( "/" | "*" ) primary )* ;
primary -> NUMBER;
```

1. An expression contains a term.
2. (*) A term consists of a factor followed by zero or more additions or subtractions. This means + - must be in between numbers.
3. A factory consists of a primary followed by zero or more multiplications or divisions.
4. A primary is a number.