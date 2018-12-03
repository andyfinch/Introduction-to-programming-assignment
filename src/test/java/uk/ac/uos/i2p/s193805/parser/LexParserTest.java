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
    void testSpaceInString() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("Response URL"));
        assertNextSymbol(lexParser, STRING);
        assertNextSymbol(lexParser, END);
        
    }

    @Test
    void testSlashInString() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("/answer/d3ae45"));
        assertNextSymbol(lexParser, STRING);
        assertNextSymbol(lexParser, NUMBER);
        assertNextSymbol(lexParser, STRING);
        assertNextSymbol(lexParser, NUMBER);
        assertNextSymbol(lexParser, END);

    }

    @Test
    void testCombination() throws IOException {
        LexParser lexParser = new LexParser(new StringReader("{\"abc\":123"));
        assertNextSymbol(lexParser, JSONSymbol.Type.OPEN_BRACE);
        assertNextSymbol(lexParser, JSONSymbol.Type.QUOTE);
        assertNextSymbol(lexParser, JSONSymbol.Type.STRING, "abc");
        assertNextSymbol(lexParser, JSONSymbol.Type.QUOTE);
        assertNextSymbol(lexParser, JSONSymbol.Type.COLON);
        assertNextSymbol(lexParser, JSONSymbol.Type.NUMBER, "123");



    }

    @Test
    public void testMixedOther() throws IOException {
        LexParser lex = new LexParser(new StringReader("#@%!"));
        assertNextSymbol(lex, JSONSymbol.Type.OTHER, "#");
        assertNextSymbol(lex, JSONSymbol.Type.OTHER, "@");
        assertNextSymbol(lex, JSONSymbol.Type.OTHER, "%");
        assertNextSymbol(lex, JSONSymbol.Type.OTHER, "!");
        assertNextSymbol(lex, JSONSymbol.Type.END);
    }


    void assertNextSymbol(LexParser lex, JSONSymbol.Type type, String value) throws
            IOException {
        JSONSymbol symbol = lex.next();
        assertEquals(type, symbol.type);
        assertEquals(value, symbol.value);
    }

    void assertNextSymbol(LexParser lex, JSONSymbol.Type type) throws IOException {
        JSONSymbol symbol = lex.next();
        assertEquals(type, symbol.type);
    }



}