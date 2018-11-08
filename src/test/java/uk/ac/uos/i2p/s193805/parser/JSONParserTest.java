package uk.ac.uos.i2p.s193805.parser;

import org.junit.jupiter.api.Test;

import javax.json.stream.JsonParsingException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 09:53
 */

class JSONParserTest {

    @Test
    void testParser_With_Strings()  {
        JavaJSONObject javaJsonObject = JSONParser.parseJSONtoJava("{\"id\": \"s113867\",\"tasks\": [\"/task/452359-4435382-6595137\",\"/task/99012-65325148-3574826\"]}\n");
        assertEquals("s113867", javaJsonObject.getJSONStringValue("id"));
        assertEquals(2, javaJsonObject.getJSONArray("tasks").size());
        assertEquals("/task/452359-4435382-6595137", javaJsonObject.getJSONArray("tasks").get(0));
        assertEquals("/task/99012-65325148-3574826", javaJsonObject.getJSONArray("tasks").get(1));

    }

    @Test
    void testParser_With_Strings_And_Ints() {
        JavaJSONObject javaJsonObject = JSONParser.parseJSONtoJava("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
        assertEquals("add", javaJsonObject.getJSONStringValue("instruction"));
        assertEquals("/answer/d3ae45", javaJsonObject.getJSONStringValue("response URL"));
        assertEquals(2, javaJsonObject.getJSONArray("parameters").size());
        assertEquals("23", javaJsonObject.getJSONArray("parameters").get(0));
        assertEquals(45, javaJsonObject.getJSONArray("parameters").get(1));

    }

    @Test
    void testParser_With_Invalid_Key() {
        JavaJSONObject javaJsonObject = JSONParser.parseJSONtoJava("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");

        try
        {
            javaJsonObject.getJSONStringValue("XXX");
            fail("Shouldn't get here");
        } catch (IllegalArgumentException e)
        {
            assertEquals("JSON key does not exist", e.getMessage());
        }

    }

    @Test
    void testParser_With_Nested_JSON() {
        JavaJSONObject javaJsonObject = JSONParser.parseJSONtoJava("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\",\"nested\" : { \"test\" : \"hello\"}}");
        assertEquals("hello", javaJsonObject.getNestedJSONKey("test","nested"));

    }

    @Test
    void testParser_With_Multi_Nested_JSON() {
        JavaJSONObject javaJsonObject = JSONParser.parseJSONtoJava("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"firstNested\": {\"secondNested\": {\"thirdNested\": {\"test\": \"hello\"}}},\"response URL\": \"/answer/d3ae45\"}");
        assertEquals("hello", javaJsonObject.getNestedJSONKey("test", "firstNested", "secondNested", "thirdNested"));

    }

    @Test
    void testParser_With_Invalid_JSON_Missing_Comma() {

        try
        {
            JSONParser.parseJSONtoJava("{\"instruction\": \"add\"\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
            fail("Shouldn't get here");
        } catch (JsonParsingException e)
        {
            assertEquals("JSON is invalid Invalid token=STRING at (line no=1, column no=33, offset=32). Expected tokens are: [COMMA]", e.getMessage());
        }

    }

    @Test
    void testParser_With_Invalid_JSON_Extra_Brace() {

        try
        {
            JSONParser.parseJSONtoJava("{{\"instruction\": \"add\"\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
            fail("Shouldn't get here");
        } catch (JsonParsingException e)
        {
            assertEquals("JSON is invalid Invalid token=CURLYOPEN at (line no=1, column no=2, offset=1). Expected tokens are: [STRING]", e.getMessage());
        }

    }

    @Test
    void testParser_With_Invalid_JSON_Missing_Colon() {

        try
        {
            JSONParser.parseJSONtoJava("{\"instruction\" \"add\"\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
            fail("Shouldn't get here");
        } catch (JsonParsingException e)
        {
            assertEquals("JSON is invalid Invalid token=STRING at (line no=1, column no=20, offset=19). Expected tokens are: [COLON]", e.getMessage());
        }

    }

}
