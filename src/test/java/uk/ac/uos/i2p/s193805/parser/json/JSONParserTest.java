package uk.ac.uos.i2p.s193805.parser.json;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONParserTest {

    @Test
    void testEmpty() {

        JSONParser jsonParser = new JSONParser();
        Object data = jsonParser.parse(new StringReader(""));
        assertEquals("", data);

    }
}
