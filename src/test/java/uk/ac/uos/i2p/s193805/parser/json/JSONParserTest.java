package uk.ac.uos.i2p.s193805.parser.json;

import org.junit.jupiter.api.Test;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JSON;

import java.io.IOException;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONParserTest {

    @Test
    void testEmpty() throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSON data = jsonParser.parse(new StringReader("{\n" +
                "  \"instruction\": \"add\"\n" +
                "}\n"));
        assertEquals("add", data.getJSONString("instruction"));

    }
}
