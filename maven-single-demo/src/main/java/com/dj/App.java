package com.dj;

import java.io.IOException;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.github.lalyos.jfiglet.FigletFont;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
        System.out.println("Hello World!");
        Expression e = new ExpressionBuilder("3 + 5 * 2").build();
        System.out.println(e.evaluate());

        String id = NanoIdUtils.randomNanoId();
        System.out.println("Generated a random id " + id);

        System.out.println(FigletFont.convertOneLine("WolfriK"));
    }
}
