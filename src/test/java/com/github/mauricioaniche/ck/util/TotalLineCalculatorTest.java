package com.github.mauricioaniche.ck.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TotalLineCalculatorTest {
    @Test
    void commentsBeforeMethodSignatureTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("/**\r\n");
        sb.append("* Comment\r\n");
        sb.append("*/\r\n");
        sb.append("public static void method() {\r\n");
        sb.append("System.out.println(\"hello\");\r\n");
        sb.append("}\r\n");

        int totalLine = TotalLineCalculator.calculateTotalLine(sb.toString());
        Assertions.assertEquals(3, totalLine);
    }

    @Test
    void multilineInstructionTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("static List<String> getAsLastNamesLatexFree(Field field, BibEntry bibEntry) {\n");
        sb.append("return bibEntry.getField(field).stream()\n");
        sb.append("\t\t\t.map(AuthorList::parse)\n");
        sb.append("\t\t\t.map(AuthorList::latexFree)\n");
        sb.append("\t\t\t.map(AuthorList::getAuthors)\n");
        sb.append("\t\t\t.flatMap(Collection::stream)\n");
        sb.append("\t\t\t.collect(Collectors.toList());\n");
        sb.append("}\n");
        System.out.println(sb);

        int totalLine = TotalLineCalculator.calculateTotalLine(sb.toString());
        Assertions.assertEquals(8, totalLine);
    }

    @Test
    void multilineInstruction2Test() {
        String sourceCode = "public static CSVFormat getCSVFormat() {\n" +
                "        return CSVFormat.DEFAULT.builder()\n" +
                "                .setIgnoreEmptyLines(true)\n" +
                "                .setDelimiter(DELIMITER)\n" +
                "                .setEscape(ESCAPE)\n" +
                "                .setQuote(QUOTE)\n" +
                "                .setTrim(true)\n" +
                "                .build();\n" +
                "    }";
        int totalLine = TotalLineCalculator.calculateTotalLine(sourceCode);
        Assertions.assertEquals(9, totalLine);
    }

    @Test
    void emptyLinesTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("class A {\r\n");
        sb.append("\r\n");
        sb.append("\tprivate int 1;\r\n");
        sb.append("\r\n");
        sb.append("\tvoid m1() {\r\n");
        sb.append("\t\tSystem.out.println(\"aa\");\r\n");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("}\r\n");

        int totalLine = TotalLineCalculator.calculateTotalLine(sb.toString());
        Assertions.assertEquals(11, totalLine);
    }

    @Test
    void methodWithSingleLineCommentsTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("class A {\n");
        sb.append("\t// comment\n");
        sb.append("\t//comment\n");
        sb.append("private int 1;\n");
        sb.append("void m1() {\n");
        sb.append("System.out.println(\"aa\");\n");
        sb.append("}\n");

        int totalLine = TotalLineCalculator.calculateTotalLine(sb.toString());
        Assertions.assertEquals(7, totalLine);
    }
}
