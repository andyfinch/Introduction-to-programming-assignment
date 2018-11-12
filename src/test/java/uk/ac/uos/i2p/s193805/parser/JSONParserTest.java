package uk.ac.uos.i2p.s193805.parser;

import org.junit.jupiter.api.Test;

import javax.json.JsonException;
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
        JSONObject javaJsonObject = JSONToJavaParser.parseJSONtoJava("{\"id\": \"s113867\",\"tasks\": [\"/task/452359-4435382-6595137\",\"/task/99012-65325148-3574826\"]}\n");
        assertEquals("s113867", javaJsonObject.getJSONStringValue("id"));
        assertEquals(2, javaJsonObject.getJSONStringArray("tasks").size());
        assertEquals("/task/452359-4435382-6595137", javaJsonObject.getJSONStringArray("tasks").get(0));
        assertEquals("/task/99012-65325148-3574826", javaJsonObject.getJSONStringArray("tasks").get(1));

    }

    @Test
    void testParser_With_Strings1() throws Exception {
        JSONObject javaJsonObject = JSONToJavaParser.parseJSONtoJava("{\"id\": \"s113867\",\"tasks\": [\"/task/452359-4435382-6595137\",\"/task/99012-65325148-3574826\"]}\n");
        assertEquals("s113867", javaJsonObject.getJSONStringValue("id"));
        assertEquals(2, javaJsonObject.getJSONStringArray("tasks").size());
        assertEquals("/task/452359-4435382-6595137", javaJsonObject.getJSONStringArray("tasks").get(0));
        assertEquals("/task/99012-65325148-3574826", javaJsonObject.getJSONStringArray("tasks").get(1));

    }

    @Test
    void testParser_With_Ints() {
        JSONObject javaJsonObject = JSONToJavaParser.parseJSONtoJava("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
        assertEquals("add", javaJsonObject.getJSONStringValue("instruction"));
        assertEquals("/answer/d3ae45", javaJsonObject.getJSONStringValue("response URL"));
        assertEquals(2, javaJsonObject.getJSONIntArray("parameters").size());
        assertEquals(23, javaJsonObject.getJSONIntArray("parameters").get(0).intValue());
        assertEquals(45, javaJsonObject.getJSONIntArray("parameters").get(1).intValue());

    }

    @Test
    void testParser_With_Invalid_Key() {
        JSONObject javaJsonObject = JSONToJavaParser.parseJSONtoJava("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");

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
        JSONObject javaJsonObject = JSONToJavaParser.parseJSONtoJava("{\"instruction\": \"add\",\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\",\"nested\" : { \"test\" : \"hello\"}}");
        assertEquals("hello", javaJsonObject.getJSONStringValue("nested.test"));

    }


    @Test
    void testParser_With_Multi_Nested_JSON() {
        JSONObject javaJsonObject = JSONToJavaParser.parseJSONtoJava("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ],\n" +
                "  \"firstNested\": {\n" +
                "    \"secondNested\": {\n" +
                "      \"thirdNested\": {\n" +
                "        \"test\": \"hello\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}\n");
        assertEquals("hello", javaJsonObject.getJSONStringValue("firstNested.secondNested.thirdNested.test"));

    }

    @Test
    void testParser_With_Invalid_JSON_Missing_Comma() {

        try
        {
            JSONToJavaParser.parseJSONtoJava("{\"instruction\": \"add\"\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
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
            JSONToJavaParser.parseJSONtoJava("{{\"instruction\": \"add\"\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
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
            JSONObject jsonObject = JSONToJavaParser.parseJSONtoJava("{\"instruction\" \"add\"\"parameters\": [\"23\",45],\"response URL\": \"/answer/d3ae45\"}");
            fail("Shouldn't get here");
        } catch (JsonException e)
        {
            assertEquals("JSON is invalid Invalid token=STRING at (line no=1, column no=20, offset=19). Expected tokens are: [COLON]", e.getMessage());
        }

    }

    @Test
    void testParser_Validate() {
        JSONToJavaParser.validateJSONString("{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ],\n" +
                "  \"firstNested\": {\n" +
                "    \"secondNested\": {\n" +
                "      \"thirdNested\": {\n" +
                "        \"test\": \"hello\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}\n");

    }

}
