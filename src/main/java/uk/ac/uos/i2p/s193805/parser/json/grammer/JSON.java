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
        JsonMember jsonMember = jsonMember(lexParser);
        if ( jsonMember == null )
        {
            throw new RuntimeException("TODO");
        }

        return new JsonObject(jsonMember);

    }

    private JsonMember jsonMember(LexParser lexParser) throws IOException {
        String key = key(lexParser);
        if (null == key)
        {
            throw new RuntimeException("Json member must have string key");
        }
        JsonValue value = value(lexParser);
        if (null == value)
        {
            throw new RuntimeException("Json member must have value");
        }

        return new JsonMember(key, value);
    }

    /*private String key(LexParser lexParser) throws IOException {
        StringBuilder ret = new StringBuilder();
        sym: for (JSONSymbol symbol = lexParser.next(); symbol.type != JSONSymbol.Type.END; symbol = lexParser.next()) {
            switch(symbol.type) {
                case STRING:
                    ret.append(symbol.value);
                    break;
                default:
                    break sym; // to stop loop
            }
        }
        return ret.toString();
    }*/

    private String key(LexParser lex) throws IOException {
        JSONSymbol symbol = lex.next();
        if (JSONSymbol.Type.END == symbol.type) return null;

        if (symbol.type != JSONSymbol.Type.QUOTE) return null;

        symbol = lex.next();
        if (symbol.type != JSONSymbol.Type.STRING) {
            throw new IOException("Expected STRING, got " + symbol.type);
        }
        String key = symbol.value;

        symbol = lex.next();
        if (symbol.type != JSONSymbol.Type.QUOTE) {
            throw new IOException("Expected \", got " + symbol.type);
        }
        return key;
    }

    private JsonValue value(LexParser lex) throws IOException {
        JSONSymbol symbol = lex.next();
        if (JSONSymbol.Type.END == symbol.type)
        {
            throw new IOException("Expected VALUE, got " + symbol.type);
        }

        if (symbol.type != JSONSymbol.Type.QUOTE) return null;

        symbol = lex.next();
        if (symbol.type != JSONSymbol.Type.STRING) {
            throw new IOException("Expected STRING, got " + symbol.type);
        }
        String key = symbol.value;

        symbol = lex.next();
        if (symbol.type != JSONSymbol.Type.QUOTE) {
            throw new IOException("Expected \", got " + symbol.type);
        }
        return key;
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
