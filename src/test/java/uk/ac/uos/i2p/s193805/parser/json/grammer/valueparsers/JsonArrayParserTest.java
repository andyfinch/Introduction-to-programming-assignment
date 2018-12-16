package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.json.JSONParser;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class JsonArrayParserTest {

    @Test
    void testSimpleArray() throws IOException {

        JsonObject data = new JSONParser(new StringReader("{\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ]\n" +
                "}")).parse();

        assertEquals(2, data.getJsonArray("parameters").jsonValues.size());
        assertEquals("23", data.getJsonArray("parameters").getString(0));
        assertEquals(45, data.getJsonArray("parameters").getInt(1));

    }

}