package uk.ac.uos.i2p.s193805.parser.json;

import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JSON;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 08:21
 */

public class JSONParser {

    public JSON parse (Reader in) throws IOException
    {
        LexParser lexParser = new LexParser(in);
        return jsonDocument(lexParser);
    }

    private JSON jsonDocument(LexParser lexParser) throws IOException
    {
        JsonObject jsonObject = jsonObject(lexParser);
        if (null == jsonObject)
        {
            throw new RuntimeException("Not valid JSON - No {")
        }
        return new JSON(jsonObject);
    }




}


