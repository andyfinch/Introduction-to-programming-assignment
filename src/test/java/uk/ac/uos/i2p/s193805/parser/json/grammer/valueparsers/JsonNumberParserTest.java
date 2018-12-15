package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonNumber;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonString;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class JsonNumberParserTest {


    @Test
    void testInt() throws IOException {

        JsonNumber jsonNumber = getParser("1").parse();
        assertEquals(1, jsonNumber.intValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testIntLarge() throws IOException {

        JsonNumber jsonNumber = getParser("100000").parse();
        assertEquals(100000, jsonNumber.intValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testNegativeInt() throws IOException {

        JsonNumber jsonNumber = getParser("-1").parse();
        assertEquals(-1, jsonNumber.intValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testNegativeIntLarge() throws IOException {

        JsonNumber jsonNumber = getParser("-100000").parse();
        assertEquals(-100000, jsonNumber.intValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testFraction() throws IOException {

        JsonNumber jsonNumber = getParser("1.1").parse();
        assertEquals(1.1, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testFractionLarge() throws IOException {

        JsonNumber jsonNumber = getParser("100000.123456").parse();
        assertEquals(100000.123456, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testNegativeFraction() throws IOException {

        JsonNumber jsonNumber = getParser("-1.1").parse();
        assertEquals(-1.1, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testNegativeFractionLarge() throws IOException {

        JsonNumber jsonNumber = getParser("-100000.123456").parse();
        assertEquals(-100000.123456, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testExponent() throws IOException {

        JsonNumber jsonNumber = getParser("1e123").parse();
        assertEquals(1e123, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testExponentPlusSign() throws IOException {

        JsonNumber jsonNumber = getParser("1e+123").parse();
        assertEquals(1e+123, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testExponentMinusSign() throws IOException {

        JsonNumber jsonNumber = getParser("1e-123").parse();
        assertEquals(1e-123, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testFractionExponent() throws IOException {

        JsonNumber jsonNumber = getParser("1.1e123").parse();
        assertEquals(1.1e123, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testFractionExponentPlusSign() throws IOException {

        JsonNumber jsonNumber = getParser("1.1e+123").parse();
        assertEquals(1.1e+123, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testFractionExponentMinusSign() throws IOException {

        JsonNumber jsonNumber = getParser("1.1e-123").parse();
        assertEquals(1.1e-123, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testNegativeFractionExponent() throws IOException {

        JsonNumber jsonNumber = getParser("-1.1e123").parse();
        assertEquals(-1.1e123, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testNegativeFractionExponentPlusSign() throws IOException {

        JsonNumber jsonNumber = getParser("-1.1e+123").parse();
        assertEquals(-1.1e+123, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testNegativeFractionExponentMinusSign() throws IOException {

        JsonNumber jsonNumber = getParser("-1.1e-123").parse();
        assertEquals(-1.1e-123, jsonNumber.number.doubleValue());
        assertSame(jsonNumber.getJsonValueType(), JsonValue.ValueType.JSON_NUMBER);
    }

    @Test
    void testNotNumber() throws IOException {

        try {
            getParser("a").parse();
            fail();
        } catch (JsonParseException e) {
            assertEquals("Number must start with - or Integer", e.getMessage());
        }

    }

    @Test
    void testNotNumber2() throws IOException {

        try {
            getParser("1a").parse();
            fail();
        } catch (JsonParseException e) {
            assertEquals("Invalid JSON number 1a", e.getMessage());
        }

    }

    @Test
    void testMissingFraction() throws IOException {

        try {
            getParser("1.").parse();
            fail();
        } catch (JsonParseException e) {
            assertEquals("Number must follow decimal point", e.getMessage());
        }

    }

    @Test
    void testExtraFraction() throws IOException {

        try {
            getParser("1.1.1").parse();
            fail();
        } catch (JsonParseException e) {
            assertEquals("Number must follow decimal point", e.getMessage());
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

    private JsonNumberParser getParser(String json)
    {

        LexParser lexParser = new LexParser(new StringReader(json));
        PushbackLexParser pushBackLexParser = new PushbackLexParser(lexParser);
        return new JsonNumberParser(pushBackLexParser);


    }
}