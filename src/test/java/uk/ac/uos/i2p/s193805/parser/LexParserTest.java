package uk.ac.uos.i2p.s193805.parser;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 29/11/2018
 * Time: 09:32
 */

class LexParserTest {

    @Test
    void testEmpty() throws IOException {
        LexParser lexParser = new LexParser(new StringReader(""));
        JSONSymbol symbol = lexParser.next();
        assertEquals(END, symbol.type);
    }

    @Test
    void testOpenBrace() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("{"));
        JSONSymbol symbol = lexParser.next();
        assertEquals(OPEN_BRACE, symbol.type);
    }

    @Test
    void testQuote() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("\""));
        JSONSymbol symbol = lexParser.next();
        assertEquals(QUOTE, symbol.type);
    }

    @Test
    void testString() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("ABC"));
        JSONSymbol symbol = lexParser.next();
        assertEquals(STRING, symbol.type);
    }

    @Test
    void testColon() throws IOException {
        LexParser lexParser = new LexParser(new StringReader(":"));
        JSONSymbol symbol = lexParser.next();
        assertEquals(COLON, symbol.type);
    }

    @Test
    void testNumber() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("123"));
        JSONSymbol symbol = lexParser.next();
        assertEquals(NUMBER, symbol.type);
    }

    @Test
    void testComma() throws IOException {
        LexParser lexParser = new LexParser(new StringReader(","));
        JSONSymbol symbol = lexParser.next();
        assertEquals(COMMA, symbol.type);
    }

    @Test
    void testOpenArray() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("["));
        JSONSymbol symbol = lexParser.next();
        assertEquals(OPEN_ARRAY, symbol.type);
    }

    @Test
    void testCloseArray() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("]"));
        JSONSymbol symbol = lexParser.next();
        assertEquals(CLOSE_ARRAY, symbol.type);
    }

    @Test
    void testCloseBrace() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("}"));
        JSONSymbol symbol = lexParser.next();
        assertEquals(CLOSE_BRACE, symbol.type);
    }

    @Test
    void testSpace() throws IOException {
        LexParser lexParser = new LexParser(new StringReader(" "));
        JSONSymbol symbol = lexParser.next();
        assertEquals(SPACE, symbol.type);
    }

    @Test
    void testNewLine() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("\n"));
        JSONSymbol symbol = lexParser.next();
        assertEquals(SPACE, symbol.type);
    }

    @Test
    void testSpaceNewLine() throws IOException {
        LexParser lexParser = new LexParser(new StringReader(" \n "));
        JSONSymbol symbol = lexParser.next();
        assertEquals(SPACE, symbol.type);
    }

    @Test
    void testCombination() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("{\"abc\":123"));
        JSONSymbol symbol = lexParser.next();
        assertEquals(OPEN_BRACE, symbol.type);

        symbol = lexParser.next();
        assertEquals(QUOTE, symbol.type);

        symbol = lexParser.next();
        assertEquals(STRING, symbol.type);

        symbol = lexParser.next();
        assertEquals(QUOTE, symbol.type);

        symbol = lexParser.next();
        assertEquals(COLON, symbol.type);

        symbol = lexParser.next();
        assertEquals(NUMBER, symbol.type);
    }


}