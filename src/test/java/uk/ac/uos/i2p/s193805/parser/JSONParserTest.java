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
    void testParser_Validate_Simple_Key_Value() {
        JSONToJavaParser.validateJSONString("{\n" +
                "  \"instruction\": \"add\"\n" +
                "}");

    }

    @Test
    void testParser_Validate_Simple_Key_INT_Value() {
        JSONToJavaParser.validateJSONString("{\n" +
                "  \"instruction\": 10\n" +
                "}");

    }

    @Test
    void testParser_Validate_2_Simple_Key_Values() {
        JSONToJavaParser.validateJSONString("{\n" +
                "  \"instruction\": \"add\",\n" +
                "  \"another\": \"one\"\n" +
                "}");

    }

    @Test
    void testParser_Validate_Simple_Key_Values_And_Array() {
        JSONToJavaParser.validateJSONString("{\n" +
                "  \"instruction\": \"add\",\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ]\n" +
                "}");

    }

    @Test
    void testParser_Validate_Simple_Key_Values_X2_And_Array() {
        JSONToJavaParser.validateJSONString("{\n" +
                "  \"instruction\": \"add\",\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ],\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}");

    }

    @Test
    void testParser_Validate_Simple_Key_Values_X2_And_Array_And_Nested() {
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
                "}\n" +
                "\n" +
                "\n");

    }

    @Test
    void testParser_Validate_2_Simple_Key_Values_Missing_2nd_Colon() {
        try
        {
            JSONToJavaParser.validateJSONString("{\n" +
                    "  \"instruction\": \"add\",\n" +
                    "  \"another\" \"one\"\n" +
                    "}");
            fail("Should not get here");
        } catch (RuntimeException e)
        {
            assertEquals("Missing Colon", e.getMessage());
        }

    }

    @Test
    void testParser_Validate_Simple_Key_Value_Missing_Start_Brace() {
        try
        {
            JSONToJavaParser.validateJSONString("\n" +
                    "  \"instruction\": \"add\"\n" +
                    "}");
            fail();
        } catch (RuntimeException e)
        {
            assertEquals("Missing open brace", e.getMessage());
        }

    }

    @Test
    void testParser_Validate_Simple_Key_Value_Missing_Closing_Brace() {
        try
        {
            JSONToJavaParser.validateJSONString("{\n" +
                    "  \"instruction\": \"add\"\n" +
                    "");
            fail("Should not get here");
        } catch (RuntimeException e)
        {
            assertEquals("Missing closing brace", e.getMessage());
        }

    }

    @Test
    void testParser_Validate_Simple_Key_Value_Missing_Colon() {
        try
        {
            JSONToJavaParser.validateJSONString("{\n" +
                    "  \"instruction\" \"add\"\n" +
                    "");
            fail("Should not get here");
        } catch (RuntimeException e)
        {
            assertEquals("Missing Colon", e.getMessage());
        }

    }

    @Test
    void testParser_Validate_Simple_Key_Value_Missing_Value() {
        try
        {
            JSONToJavaParser.validateJSONString("{\n" +
                    "  \"instruction\": \n" +
                    "}\n");
            fail("Should not get here");
        } catch (RuntimeException e)
        {
            assertEquals("Missing JsonValue", e.getMessage());
        }

    }

    @Test
    void testParser_Validate_Simple_Key_Value_String_Value_Not_Quoted() {
        try
        {
            JSONToJavaParser.validateJSONString("{\n" +
                    "  \"instruction\": add\n" +
                    "}");
            fail("Should not get here");
        } catch (RuntimeException e)
        {
            assertEquals("String value not quoted", e.getMessage());
        }

    }

    @Test
    void testParser_Validate_Simple_Key_Value_String_Value_End_Quote_Missing() {
        try
        {
            JSONToJavaParser.validateJSONString("{\n" +
                    "  \"instruction\": \"add\n" +
                    "}");
            fail("Should not get here");
        } catch (RuntimeException e)
        {
            assertEquals("String value end quote missing", e.getMessage());
        }

    }

    @Test
    void testParser_Validate_Simple_Array() {
        JSONToJavaParser.validateJSONString("{\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ]\n" +
                "}");

    }

    @Test
    void testParser_Validate_Simple_Array_X_2() {
        JSONToJavaParser.validateJSONString("{\n" +
                "  \"parameters\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "    ],\n" +
                "  \"parametersAgain\": [\n" +
                "    \"23\",\n" +
                "    45\n" +
                "  ]\n" +
                "}");

    }

    @Test
    void testParser_Validate_Simple_Array_INT() {
        JSONToJavaParser.validateJSONString("{\n" +
                "  \"parameters\": [\n" +
                "    23,\n" +
                "    45\n" +
                "  ]\n" +
                "}");

    }

    @Test
    void testParser_Validate_Simple_Array_Missing_Start_Quote() {
        try
        {
            JSONToJavaParser.validateJSONString("{\n" +
                    "  \"parameters\": [\n" +
                    "    23\",\n" +
                    "    45\n" +
                    "  ]\n" +
                    "}");
            fail("Should not get here");
        } catch (RuntimeException e)
        {
            assertEquals("String value end quote missing", e.getMessage());
        }

    }

    @Test
    void testParser_Validate_Simple_Array_Missing_End_Quote() {
        try
        {
            JSONToJavaParser.validateJSONString("{\n" +
                    "  \"parameters\": [\n" +
                    "    \"23,\n" +
                    "    45\n" +
                    "  ]\n" +
                    "}");
            fail("Should not get here");
        } catch (RuntimeException e)
        {
            assertEquals("String value end quote missing", e.getMessage());
        }

    }

    @Test
    void testParser_Validate_Simple_Array_Missing_Square_Bracket() {
        try
        {
            JSONToJavaParser.validateJSONString("{\n" +
                    "  \"parameters\": \n" +
                    "    23,\n" +
                    "    45\n" +
                    "    ]\n" +
                    "}");
            fail("Should not get here");
        } catch (RuntimeException e)
        {
            assertEquals("Array missing [", e.getMessage());
        }

    }



    /*@Test
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

    }*/

}
