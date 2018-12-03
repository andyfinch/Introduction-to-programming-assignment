package uk.ac.uos.i2p.s193805.parser.json;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JSON;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class JSONParserTest {

    @Test
    void testSimpleKeyString() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSON data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\": \"add\"\n" +
                "}\n"));
        assertEquals("add", data.getJSONString("instruction"));

    }

    @Test
    void testSimpleKeyString_X2() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSON data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}"));
        assertEquals("/answer/d3ae45", data.getJSONString("response URL"));

    }

    @Test
    void testSimpleKeyNumber() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSON data = jsonParser.parse(new StringReader("{\n" +
                "  \"number\": 12\n" +
                "}"));
        assertEquals(12, data.getJSONNumber("number").intValue());

    }

    @Test
    void testKeyValueNotNumber() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSON data = jsonParser.parse(new StringReader("{\n" +
                "  \"number\": \"A\"\n" +
                "}"));

        try {
            data.getJSONNumber("number");
            fail("shouldn't get here");
        }
        catch (RuntimeException re)
        {
            assertEquals("Requested Number is not a JSON Number. It is a class java.lang.String", re.getMessage());
        }


    }

    @Test
    void testKeyStringNumberCombo() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSON data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"number\" : 12,\n" +
                "  \"response URL\": \"/answer/d3ae45\",\n" +
                "  \"server\": \"Test1Server2\"\n" +
                "}\n"));
        assertEquals("add", data.getJSONString("instruction"));
        assertEquals("/answer/d3ae45", data.getJSONString("response URL"));
        assertEquals(12, data.getJSONNumber("number").intValue());
        assertEquals("Test1Server2", data.getJSONString("server"));

    }

    @Test
    void testSimpleKeyBoolean() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSON data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\": true\n" +
                "}\n"));
        assertEquals(true, data.getJsonBoolean("instruction"));

    }

    @Test
    void testSimpleKeyNull() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSON data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\": null\n" +
                "}\n"));
        assertNull(data.jsonObject.getJsonValue("instruction"));

    }
}
