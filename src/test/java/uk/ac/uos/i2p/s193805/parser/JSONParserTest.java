package uk.ac.uos.i2p.s193805.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 09:53
 */

class JSONParserTest {

    @Test
    void testParser_With_Strings() {
        JSONParser jsonParser = new JSONParser("{\"id\": \"s113867\",\"tasks\": [\"/task/452359-4435382-6595137\",\"/task/99012-65325148-3574826\"]}\n");
        assertEquals("s113867", jsonParser.getJSONStringValue("id"));
        assertEquals(2, jsonParser.getJSONArray("tasks").size());
        assertEquals("/task/452359-4435382-6595137", jsonParser.getJSONArray("tasks").get(0));
        assertEquals("/task/99012-65325148-3574826", jsonParser.getJSONArray("tasks").get(1));

    }

    @Test
    void testParser_With_Strings_And_Ints() {
        JSONParser jsonParser = new JSONParser("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
        assertEquals("add", jsonParser.getJSONStringValue("instruction"));
        assertEquals("/answer/d3ae45", jsonParser.getJSONStringValue("response URL"));
        assertEquals(2, jsonParser.getJSONArray("parameters").size());
        assertEquals("23", jsonParser.getJSONArray("parameters").get(0));
        assertEquals(45, jsonParser.getJSONArray("parameters").get(1));

    }

    @Test
    void testParser_With_Invalid_Key() {
        JSONParser jsonParser = new JSONParser("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");

        try
        {
            jsonParser.getJSONStringValue("XXX");
            fail("Shouldn't get here");
        } catch (IllegalArgumentException e)
        {
            assertEquals("JSON key does not exist", e.getMessage());
        }

    }

    @Test
    void testParser_With_Nested_JSON() {
        JSONParser jsonParser = new JSONParser("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\",\"nested\" : { \"test\" : \"hello\"}}");
        JSONParser nestedJSONObject = new JSONParser(jsonParser.getNestedJSONObject("nested"));
        assertEquals("hello", nestedJSONObject.getJSONStringValue("test"));

    }

}
