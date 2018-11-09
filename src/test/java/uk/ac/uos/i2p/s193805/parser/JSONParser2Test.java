package uk.ac.uos.i2p.s193805.parser;

import org.junit.jupiter.api.Test;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 08/11/2018
 * Time: 09:20
 */

class JSONParser2Test {

    @Test
    void name() throws Exception {

        JSONObject jsonObject = JSONToJavaParser.parseJSONtoJava("{\n" +
                "  \"instruction\": \"add\",\n" +
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
                "}");
        
        System.out.println("");
    }

   
}