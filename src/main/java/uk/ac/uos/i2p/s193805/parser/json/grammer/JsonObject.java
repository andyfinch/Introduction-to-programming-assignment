package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

public class JsonObject implements JsonValue {

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

        JsonValue jsonValue = JsonValueBuilder.buildJsonValue(lex);

        if ( jsonValue == null) return null;

        return jsonValue;

    }


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

    public JsonValue getJsonValue(String key, Class<? extends JsonValue> JsonType) {

        JsonValue jsonValue = getJsonValue(key);

        if (!(JsonType.isInstance(jsonValue)))
        {
            throw new RuntimeException("Requested key is not a " + jsonValue.getClass().getSimpleName() + ". It is a " + JsonType.getSimpleName());
        }

        if (!jsonValueMap.containsKey(key))
        {
            throw new RuntimeException("Key " + key + " does not exist");
        }

        return jsonValue;

    }

    public JsonString getJSONString(String keyName) {

        return (JsonString) getJsonValue(keyName, JsonString.class);

    }

    public String getString(String keyName) {

        return getJSONString(keyName).stringValue;

    }

    public JsonNumber getJSONNumber(String keyName) {

        return (JsonNumber) getJsonValue(keyName, JsonNumber.class);

    }

    public int getInt(String keyName) {

        return getJSONNumber(keyName).intValue();

    }

    public JsonBoolean getJsonBoolean(String keyName) {

        return (JsonBoolean) getJsonValue(keyName, JsonBoolean.class);

    }

    public boolean getBoolean(String keyName) {

        return getJsonBoolean(keyName).booleanValue;

    }

    public JsonObject getJsonObject(String keyName) {

        return (JsonObject) getJsonValue(keyName, JsonObject.class);

    }

    public JsonArray getJsonArray(String keyName) {

        return (JsonArray) getJsonValue(keyName, JsonArray.class);

    }

    public List<JsonValue> getJsonArrayList(String keyName) {
        JsonValue jsonValue = getJsonValue(keyName);
        return getJsonArray(keyName).jsonValues;
    }

    public List<String> getJsonArrayStringList(String keyName) {
        JsonArray jsonArray = getJsonArray(keyName);

        List<String> valuesAsStrings = new ArrayList<>();

        for (JsonValue value : jsonArray.jsonValues)
        {
            valuesAsStrings.add(String.valueOf(value));
        }

        return valuesAsStrings;

    }

    public List<Object> getJsonArrayObjectList(String keyName) {
        JsonArray jsonArray = getJsonArray(keyName);

        return new ArrayList<>(jsonArray.jsonValues);

    }



}
