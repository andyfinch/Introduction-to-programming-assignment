package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

public class JsonObject implements JsonValue {

    //has members
    //public final JsonMember jsonMember;
    public final Map<String, JsonValue> jsonValueMap;
    //public final PushbackLexParser pushbackLexParser;


    public JsonObject(Map<String, JsonValue> jsonValueMap) throws IOException {

        this.jsonValueMap = jsonValueMap;

    }


    public JsonValue getJsonValue(String key) {
        if (!jsonValueMap.containsKey(key))
        {
            throw new JsonParseException("Key " + key + " does not exist");
        }

        JsonValue jsonValue = jsonValueMap.get(key);

        if (jsonValue == null)
        {
            return null;
        }

        return jsonValue;

    }

    private JsonValue getJsonValue(String key, Class<? extends JsonValue> JsonType) {

        JsonValue jsonValue = getJsonValue(key);

        if (!(JsonType.isInstance(jsonValue)))
        {
            throw new JsonParseException("Requested key is not a " + jsonValue.getClass().getSimpleName() + ". It is a " + JsonType.getSimpleName());
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
