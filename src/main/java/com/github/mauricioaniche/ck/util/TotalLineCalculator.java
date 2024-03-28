package com.github.mauricioaniche.ck.util;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TotalLineCalculator {
    private static final Logger log = LoggerFactory.getLogger(TotalLineCalculator.class);

    public static int calculateTotalLine(String sourceCode) {
        try {
            InputStream is = IOUtils.toInputStream(sourceCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return getTotalNumberOfLines(reader);
        } catch (IOException e) {
            log.error("Error when counting total lines", e);
            return 0;
        }
    }

    private static int getTotalNumberOfLines(BufferedReader bReader) throws IOException {
        int count = 0;
        String line = null;
        while ((line = bReader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("/**") || line.startsWith("*") || line.startsWith("*/")) {
                continue;
            }
            //System.out.println(line);
            count++;
        }
        return count;
    }

}
