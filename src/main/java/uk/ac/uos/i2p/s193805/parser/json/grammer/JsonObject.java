package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private JsonValue getJsonValue(String key, JsonValue.ValueType jsonType) {

        JsonValue jsonValue = getJsonValue(key);

        if ( jsonValue.getJsonValueType() != jsonType )
        {
            throw new JsonParseException("Requested key is not a " + jsonType + ". It is a " + jsonValue.getJsonValueType());
        }

        return jsonValue;

    }

    public JsonString getJSONString(String keyName) {

        return (JsonString) getJsonValue(keyName, ValueType.JSON_STRING);

    }

    public String getString(String keyName) {

        return getJSONString(keyName).stringValue;

    }

    public JsonNumber getJSONNumber(String keyName) {

        return (JsonNumber) getJsonValue(keyName, ValueType.JSON_NUMBER);

    }

    public int getInt(String keyName) {

        return getJSONNumber(keyName).intValue();

    }

    public JsonBoolean getJsonBoolean(String keyName) {

        return (JsonBoolean) getJsonValue(keyName, ValueType.JSON_BOOLEAN);

    }

    public boolean getBoolean(String keyName) {

        return getJsonBoolean(keyName).booleanValue;

    }

    public JsonObject getJsonObject(String keyName) {

        return (JsonObject) getJsonValue(keyName, ValueType.JSON_OBJECT);

    }

    public JsonArray getJsonArray(String keyName) {

        return (JsonArray) getJsonValue(keyName, ValueType.JSON_ARRAY);

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

    @Override
    public ValueType getJsonValueType() {
        return ValueType.JSON_OBJECT;
    }


}
