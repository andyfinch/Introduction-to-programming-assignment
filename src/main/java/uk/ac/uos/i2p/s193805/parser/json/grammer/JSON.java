package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.LexParser;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

public class JSON {

    public final JsonObject jsonObject;

    public JSON(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    private JSON json(LexParser lexParser) throws IOException
    {
        JsonObject jsonObject = jsonObject(lexParser);

        return new JSON(jsonObject);

    }

   /* //Element can container object,array,boolean,string,number,null...
    private JsonValue jsonValue(LexParser lexParser) throws IOException
    {
        JsonObject jsonObject = jsonObject(lexParser);
        if ( null == jsonObject) return null;

        return new JsonValue();
    }*/

    private JsonObject jsonObject(LexParser lexParser) throws IOException
    {
        JsonMember jsonMember = jsonMember();
        if ( jsonMember == null );

        return new JsonObject(jsonMember);

    }

    /*private JsonArray jsonArray(LexParser lexParser) throws IOException
    {
        JSONSymbol jsonSymbol = lexParser.next();
        if ( jsonSymbol.type != JSONSymbol.Type.OPEN_ARRAY)
        {
            return null;
        }
        return new JsonArray();

    }

    private JsonBoolean jsonBoolean(LexParser lexParser) throws IOException
    {
        JSONSymbol jsonSymbol = lexParser.next();
        if (jsonSymbol.type == JSONSymbol.Type.STRING
                && (jsonSymbol.value.equalsIgnoreCase("true")
                || jsonSymbol.value.equalsIgnoreCase("false")))
        {
                return new JsonBoolean();
        }

        return null;

    }*/



}
