package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

public class JSON {

    public final JsonObject jsonObject;

    public JSON(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSON(PushbackLexParser pushbackLexParser) throws IOException {
        this.jsonObject = jsonObject(pushbackLexParser);
    }

    /*private JSON json(PushbackLexParser lexParser) throws IOException
    {
        JsonObject jsonObject = jsonObject(lexParser);

        return new JSON(jsonObject);

    }*/

    private JsonObject jsonObject(PushbackLexParser lexParser) throws IOException
    {
        JSONSymbol symbol = lexParser.next();

        if ( symbol.type != OPEN_BRACE)
        {
            throw new RuntimeException("JSON Object must start with {");
        }

        JsonObject jsonObject = new JsonObject(lexParser);

        return jsonObject;

       /* JsonMember jsonMember = jsonMember(lexParser);
        if ( jsonMember == null )
        {
            throw new RuntimeException("TODO");
        }

        return new JsonObject(jsonMember);*/

    }

    private JsonMember jsonMember(PushbackLexParser lexParser) throws IOException {
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

    private String key(PushbackLexParser lex) throws IOException {
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

        symbol = lex.next();
        if ( symbol.type == SPACE)
        {
            symbol = lex.next();
        }

        if (symbol.type != COLON)
        {
            throw new RuntimeException("Key must be followed by :");
        }

        return key;
    }

    private JsonValue value(PushbackLexParser lex) throws IOException {
        JSONSymbol symbol = lex.next();
        if (JSONSymbol.Type.END == symbol.type)
        {
            throw new IOException("Expected VALUE, got " + symbol.type);
        }

        JsonValue jsonValue = new JsonValue(lex);

        return jsonValue;
    }

    public String getJSONString(String keyName)
    {
        return (String) jsonObject.jsonValueMap.get(keyName).jsonValue;
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
