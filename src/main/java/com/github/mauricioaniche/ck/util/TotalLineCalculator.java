package com.github.mauricioaniche.ck.util;

import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotalLineCalculator {
    private static final Logger log = LoggerFactory.getLogger(TotalLineCalculator.class);

    public static int calculateTotalLine(String sourceCode) {
        try {
            ASTParser parser = ASTParser.newParser(AST.JLS20);
            parser.setSource(sourceCode.toCharArray());

            CompilationUnit cu = (CompilationUnit) parser.createAST(null);

            MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
            cu.accept(visitor);

            return visitor.getTotalLines();
        } catch (Exception e) {
            System.err.println("Error when counting total lines: " + e.getMessage());
            return 0;
        }
    }

    private static class MethodDeclarationVisitor extends ASTVisitor {
        private int totalLines = 0;

        @Override
        public boolean visit(MethodDeclaration node) {
            int startLine = node.getStartPosition();
            int endLine = startLine + node.getLength();

            CompilationUnit cu = (CompilationUnit) node.getRoot();
            int startLineNumber = cu.getLineNumber(startLine);
            int endLineNumber = cu.getLineNumber(endLine);
            System.out.println("node : " + node);
            System.out.println("startLineNumber : " + startLineNumber);
            System.out.println("endLineNumber : " + endLineNumber + "\n\n");

            totalLines = endLineNumber - startLineNumber + 1; // Include the last line

            return super.visit(node);
        }

        public int getTotalLines() {
            return totalLines;
        }
    }

}
