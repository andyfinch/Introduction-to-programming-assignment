package uk.ac.uos.i2p.s193805.parser;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.NUMBER;
import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.STRING;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 29/11/2018
 * Time: 09:32
 */

class PushbackLexParserTest {

    @Test
    void testSimpleString() throws IOException {
        PushbackLexParser lexParser = new PushbackLexParser(new LexParser(new StringReader("A")));
        JSONSymbol symbol = lexParser.next();
        assertEquals(STRING, symbol.type);
    }

    @Test
    void testSimpleStringNumber() throws IOException {
        PushbackLexParser lexParser = new PushbackLexParser(new LexParser(new StringReader("A1")));
        JSONSymbol symbol = lexParser.next();
        assertEquals(STRING, symbol.type);
        symbol = lexParser.next();
        assertEquals(NUMBER, symbol.type);
    }

    @Test
    void testSimpleStringPushback() throws IOException {
        PushbackLexParser lexParser = new PushbackLexParser(new LexParser(new StringReader("A1")));
        JSONSymbol symbol = lexParser.next();
        assertEquals(STRING, symbol.type);
        lexParser.unread(symbol);
        symbol = lexParser.next();
        assertEquals(STRING, symbol.type);
        assertEquals("A", symbol.value);
        symbol = lexParser.next();
        assertEquals(NUMBER, symbol.type);
    }
}