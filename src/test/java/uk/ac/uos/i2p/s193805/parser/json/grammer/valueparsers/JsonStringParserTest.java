package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonString;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class JsonStringParserTest {


    @Test
    void testSimpleString() throws IOException {

        JsonString jsonString = getParser("\"String\"").parse();
        assertEquals("String", jsonString.stringValue);
        assertSame(jsonString.getJsonValueType(), JsonValue.ValueType.JSON_STRING);
    }

    @Test
    void testStringSpaces() throws IOException {

        JsonString jsonString = getParser("\"This is a String\"").parse();
        assertEquals("This is a String", jsonString.stringValue);
        assertSame(jsonString.getJsonValueType(), JsonValue.ValueType.JSON_STRING);
    }

    @Test
    void testMissingStartQuote() throws IOException {

        try {
            getParser("String\"").parse();
            fail();
        } catch (JsonParseException e) {
            assertEquals("Json string must start with \"", e.getMessage());
        }

    }

    @Test
    void testMissingEndQuote() throws IOException {

        try {
            getParser("\"String").parse();
            fail();
        } catch (JsonParseException e) {
            assertEquals("Json string must end with \"", e.getMessage());
        }

    }

    @Test
    void testMissingQuotes() throws IOException {

        try {
            getParser("String").parse();
            fail();
        } catch (JsonParseException e) {
            assertEquals("Json string must start with \"", e.getMessage());
        }

    }

    private JsonStringParser getParser(String json)
    {

        LexParser lexParser = new LexParser(new StringReader(json));
        PushbackLexParser pushBackLexParser = new PushbackLexParser(lexParser);
        return new JsonStringParser(pushBackLexParser);


    }
}