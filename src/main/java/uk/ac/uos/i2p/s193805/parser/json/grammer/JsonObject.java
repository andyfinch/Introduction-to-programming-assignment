package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

public class JsonObject {

    //has members
    //public final JsonMember jsonMember;
    public final Map<String, JsonValue> jsonValueMap = new HashMap<>();


    public JsonObject(PushbackLexParser pushbackLexParser) throws IOException {

        JsonMember jsonMember;
        
        while ( (jsonMember = jsonMember(pushbackLexParser)) != null)
        {
            //JsonMember jsonMember = jsonMember(pushbackLexParser);
            jsonValueMap.put(jsonMember.key, jsonMember.jsonValue);

        }


    }


    private JsonMember jsonMember(PushbackLexParser lexParser) throws IOException {

        JSONSymbol symbol = lexParser.nextSkipSpaces();

        if ( symbol.type == JSONSymbol.Type.END || symbol.type == JSONSymbol.Type.CLOSE_BRACE)
        {
            return null;
        }
        lexParser.unread(symbol);

        String key = key(lexParser);
        if (null == key)
        {
            throw new RuntimeException("Json member must have string key");
        }
        JsonValue value = value(lexParser);

        symbol = lexParser.nextSkipSpaces();
        if ( (symbol.type != JSONSymbol.Type.CLOSE_BRACE && symbol.type != JSONSymbol.Type.END) && symbol.type != JSONSymbol.Type.COMMA)
        {
            throw new RuntimeException("Members must be seperated by commas, found symbol " + symbol.type.name() + " symbol value " + symbol.value );
        }
        if ( symbol.type == CLOSE_BRACE)
        {
            lexParser.unread(symbol);
        }

        return new JsonMember(key, value);
    }

    private String key(PushbackLexParser lex) throws IOException {
        JSONSymbol symbol = lex.nextSkipSpaces();
        StringBuilder key = new StringBuilder();

        if (JSONSymbol.Type.END == symbol.type) return null;

        if (symbol.type != JSONSymbol.Type.QUOTE) return null;

        //lex.nextSkipSpaces();

        while ( (symbol = lex.nextSkipSpaces()).type != JSONSymbol.Type.QUOTE)
        {
            if ( symbol.type == JSONSymbol.Type.END)
            {
                throw new RuntimeException("Quoted String must end with \"");
            }
            key.append(symbol.value);
        }

        symbol = lex.nextSkipSpaces();

        if (symbol.type != COLON)
        {
            throw new RuntimeException("Key must be followed by :");
        }

        return key.toString();
    }

    private JsonValue value(PushbackLexParser lex) throws IOException {

        JsonValue jsonValue = new JsonValue(lex);

        if ( jsonValue.object == null) return null;

        return jsonValue;

    }

    /*public Object getJsonValue(String key)
    {
        if (!jsonValueMap.containsKey(key) )
        {
            throw new RuntimeException("Key " + key + " does not exist");
        }

        JsonValue jsonValue = jsonValueMap.get(key);

        if ( jsonValue == null)
        {
            return null;
        }

        return jsonValue.object;

    }*/

    public JsonValue getJsonValue(String key) {
        if (!jsonValueMap.containsKey(key))
        {
            throw new RuntimeException("Key " + key + " does not exist");
        }

        JsonValue jsonValue = jsonValueMap.get(key);

        if (jsonValue == null)
        {
            return null;
        }

        return jsonValue;

    }

    public String getJSONString(String keyName) {

        JsonValue jsonValue = getJsonValue(keyName);

        return jsonValue.getJSONString();

    }

    public JsonNumber getJSONNumber(String keyName) {

        JsonValue jsonValue = getJsonValue(keyName);

        return jsonValue.getJSONNumber();

    }

    public Boolean getJsonBoolean(String keyName) {

        JsonValue jsonValue = getJsonValue(keyName);

        return jsonValue.getJsonBoolean();

    }

    public JsonObject getJsonObject(String keyName) {

        JsonValue jsonValue = getJsonValue(keyName);

        return jsonValue.getJsonObject();

    }

    public JsonArray getJsonArray(String keyName) {
        JsonValue jsonValue = getJsonValue(keyName);

        return jsonValue.getJsonArray();

    }

    public List<JsonValue> getJsonArrayList(String keyName) {
        JsonValue jsonValue = getJsonValue(keyName);

        return jsonValue.getJsonArray().jsonValues;

    }

    public List<String> getJsonArrayStringList(String keyName) {
        JsonValue jsonValue = getJsonValue(keyName);

        List<String> valuesAsStrings = new ArrayList<>();

        for (JsonValue value : jsonValue.getJsonArray().jsonValues)
        {
            valuesAsStrings.add(value.getJSONString());
        }


        return valuesAsStrings;

    }

    public List<Object> getJsonArrayObjectList(String keyName) {
        JsonValue jsonValue = getJsonValue(keyName);

        return new ArrayList<>(jsonValue.getJsonArray().jsonValues);

    }



}
