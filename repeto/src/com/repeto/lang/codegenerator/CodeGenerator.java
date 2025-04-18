package com.repeto.lang.codegenerator;

import com.repeto.lang.parser.Expr;

public class CodeGenerator {
    private final Expr expr;

    public CodeGenerator(Expr expr) {
        this.expr = expr;
    }

    public String generate() {
        ASTWalker astWalker = new ASTWalker();

        StringBuilder code = new StringBuilder();

        code.append("#include <stdio.h>\n");
        code.append("#include \"../repeto/library/expr.h\"\n");
        code.append("#include \"../repeto/library/io.h\"\n");
        code.append("int main() {\n");
        code.append(astWalker.walk(this.expr));
        code.append("return 0;\n");
        code.append("}");

        return code.toString();
    }
}
