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

        return new JsonValue(lex);
    }

    /*public String getJSONString(String keyName)
    {
        Object jsonString = jsonObject.getJsonValue(keyName);

        if ( !(jsonString instanceof String))
        {
            throw new RuntimeException("Requested String is not a JSON String. It is a" + jsonString.getClass());
        }

        return String.valueOf(jsonString);
    }

    public Integer getJSONNumber(String keyName) {

        Object jsonInt = jsonObject.getJsonValue(keyName);

        if (!(jsonInt instanceof Number))
        {
            throw new RuntimeException("Requested Number is not a JSON Number. It is a " + jsonInt.getClass());
        }

        try {
            return Integer.valueOf(String.valueOf(jsonInt));
        }
        catch (NumberFormatException nfe)
        {
            throw new RuntimeException("Cannot convert number to Integer" + jsonInt);
        }

    }

    public Boolean getJsonBoolean(String keyName) {

        Object jsonBoolean = jsonObject.getJsonValue(keyName);

        if (!(jsonBoolean instanceof Boolean))
        {
            throw new RuntimeException("Requested Boolean is not a JSON Boolean. It is a " + jsonBoolean.getClass());
        }

        String booleanString = String.valueOf(jsonBoolean);
        if ( booleanString.equals("true") || booleanString.equals("false"))
        {
            return Boolean.valueOf(booleanString);
        }
        else
        {
            throw new RuntimeException("Requested Boolean is not a JSON Boolean. It is a " + jsonBoolean.getClass());

        }


    }

    public JsonObject getJsonObject(String keyName)
    {
        Object JSONObject = jsonObject.getJsonValue(keyName);

        if ( !(JSONObject instanceof JsonObject))
        {
            throw new RuntimeException("Requested Json Object is not a JSON Object. It is a " + JSONObject.getClass());
        }

        return (JsonObject) JSONObject;

    }*/



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
