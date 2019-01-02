package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JsonObject object holding a keyValue Hashmap
 */
public class JsonObject implements JsonValue {

    /**
     * The Json value map.
     */
    public final Map<String, JsonValue> jsonValueMap;


    /**
     * Instantiates a new Json object.
     *
     * @param jsonValueMap the json value map
     */
    public JsonObject(Map<String, JsonValue> jsonValueMap) {

        this.jsonValueMap = jsonValueMap;

    }


    /**
     * Gets json value.
     *
     * @param key the key
     * @return the json value
     */
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

    /**
     * Gets json string.
     *
     * @param keyName the key name
     * @return the json string
     */
    public JsonString getJSONString(String keyName) {

        return (JsonString) getJsonValue(keyName, ValueType.JSON_STRING);

    }

    /**
     * Gets string.
     *
     * @param keyName the key name
     * @return the string
     */
    public String getString(String keyName) {

        return getJSONString(keyName).stringValue;

    }

    /**
     * Gets json number.
     *
     * @param keyName the key name
     * @return the json number
     */
    public JsonNumber getJSONNumber(String keyName) {

        return (JsonNumber) getJsonValue(keyName, ValueType.JSON_NUMBER);

    }

    /**
     * Gets int.
     *
     * @param keyName the key name
     * @return the int
     */
    public int getInt(String keyName) {

        return getJSONNumber(keyName).intValue();

    }

    /**
     * Gets json boolean.
     *
     * @param keyName the key name
     * @return the json boolean
     */
    public JsonBoolean getJsonBoolean(String keyName) {

        return (JsonBoolean) getJsonValue(keyName, ValueType.JSON_BOOLEAN);

    }

    /**
     * Gets boolean.
     *
     * @param keyName the key name
     * @return the boolean
     */
    public boolean getBoolean(String keyName) {

        return getJsonBoolean(keyName).booleanValue;

    }

    /**
     * Gets json object.
     *
     * @param keyName the key name
     * @return the json object
     */
    public JsonObject getJsonObject(String keyName) {

        return (JsonObject) getJsonValue(keyName, ValueType.JSON_OBJECT);

    }

    /**
     * Gets json array.
     *
     * @param keyName the key name
     * @return the json array
     */
    public JsonArray getJsonArray(String keyName) {

        return (JsonArray) getJsonValue(keyName, ValueType.JSON_ARRAY);

    }


    /**
     * Gets json array string list.
     *
     * @param keyName the key name
     * @return the json array string list
     */
    public List<String> getJsonArrayStringList(String keyName) {
        JsonArray jsonArray = getJsonArray(keyName);

        List<String> valuesAsStrings = new ArrayList<>();

        for (JsonValue value : jsonArray.jsonValues)
        {
            valuesAsStrings.add(String.valueOf(value));
        }

        return valuesAsStrings;

    }

    /**
     * Gets json array object list.
     *
     * @param keyName the key name
     * @return the json array object list
     */
    public List<Object> getJsonArrayObjectList(String keyName) {
        JsonArray jsonArray = getJsonArray(keyName);

        return new ArrayList<>(jsonArray.jsonValues);

    }

    /**
     * To check what type of JsonValue this is.
     *
     * @return the type of JsonValue
     */
    @Override
    public ValueType getJsonValueType() {
        return ValueType.JSON_OBJECT;
    }


}
