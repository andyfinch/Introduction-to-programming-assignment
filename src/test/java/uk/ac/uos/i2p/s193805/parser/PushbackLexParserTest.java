package uk.ac.uos.i2p.s193805.parser;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 29/11/2018
 * Time: 09:32
 */

class PushbackLexParserTest {

    @Test
    void testEmpty() throws IOException {
        LexParser lexParser = new LexParser(new StringReader(""));
        JSONSymbol symbol = lexParser.next();
        assertEquals(END, symbol.type);
        //PushbackLexParser pushbackLexParser = new PushbackLexParser()
    }
}