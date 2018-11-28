package uk.ac.uos.i2p.s193805.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 08/11/2018
 * Time: 09:20
 */

class JSONParser2Test {

    @Test
    void name() throws Exception {

        String json = "{\n" +
                "  \"instruction\" : \"add\",\n" +
                "  \"response URL\": \"/answer/d3ae45\"\n" +
                "}";

        JSONToJavaParser2 jsonToJavaParser = new JSONToJavaParser2(new StringReader(json));
        jsonToJavaParser.parse();
        
    }

    @Test
    void testOpenBrace() throws Exception {

        String json = "{";

        JSONToJavaParser2 jsonToJavaParser = new JSONToJavaParser2(new StringReader(json));
        JSONSymbol jsonSymbol = jsonToJavaParser.next();
        assertEquals(JSONSymbol.Type.OPEN_BRACE, jsonSymbol.type);

    }

    @Test
    void testClosedBrace() throws Exception {

        String json = "}";

        JSONToJavaParser2 jsonToJavaParser = new JSONToJavaParser2(new StringReader(json));
        JSONSymbol jsonSymbol = jsonToJavaParser.next();
        assertEquals(JSONSymbol.Type.CLOSE_BRACE, jsonSymbol.type);

    }

    @Test
    void testColon() throws Exception {

        String json = ":";

        JSONToJavaParser2 jsonToJavaParser = new JSONToJavaParser2(new StringReader(json));
        JSONSymbol jsonSymbol = jsonToJavaParser.next();
        assertEquals(JSONSymbol.Type.COLON, jsonSymbol.type);

    }

    @Test
    void testStringValue() throws Exception {

        String json = "Hello";

        JSONToJavaParser2 jsonToJavaParser = new JSONToJavaParser2(new StringReader(json));
        JSONSymbol jsonSymbol = jsonToJavaParser.next();
        assertEquals(JSONSymbol.Type.STRING_VALUE, jsonSymbol.type);

    }

   
}