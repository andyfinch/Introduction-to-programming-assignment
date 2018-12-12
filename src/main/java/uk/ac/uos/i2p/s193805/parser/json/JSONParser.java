package uk.ac.uos.i2p.s193805.parser.json;

import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JSON;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 08:21
 */

public class JSONParser {

    JsonValue jsonValue;
    Reader in;

    public JSONParser(Reader in) {
        this.in = in;
    }

    /*public JsonObject parse (Reader in) throws IOException
    {
        LexParser lexParser = new LexParser(in);
        PushbackLexParser pushBackLexParser = new PushbackLexParser(lexParser);
        return jsonDocument(pushBackLexParser);
    }*/

    public void parse () throws IOException
    {
        LexParser lexParser = new LexParser(in);
        PushbackLexParser pushBackLexParser = new PushbackLexParser(lexParser);
        this.jsonValue = jsonDocument(pushBackLexParser);
        //return jsonDocument(pushBackLexParser);
    }

    public JsonObject getJsonObject()
    {
        return (JsonObject) jsonValue;
    }


    private JsonValue jsonDocument(PushbackLexParser lexParser) throws IOException
    {
        JSON json = new JSON(lexParser);
        json.parse();
        return json.jsonValue;

        /*if ( json.jsonValue == null)
        {
            throw new JsonParseException("Not valid JSON - No {");
        }*/

        /*if ( json.jsonValue instanceof JsonObject)
        {
           return (JsonObject) json.jsonValue;
        }
        else
        {
            throw new IllegalArgumentException("Request Json Object is not a Json Object. It is a " + json.jsonValue.getClass().getSimpleName());
        }*/


    }




}


