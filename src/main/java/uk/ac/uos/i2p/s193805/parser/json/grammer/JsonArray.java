package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonArray object that holds a List of contained values
 */
public class JsonArray implements JsonValue {

    /**
     * The Json values.
     */
    public final List<JsonValue> jsonValues;

    /**
     * Instantiates a new Json array.
     *
     * @param jsonValues the json values
     * @throws IOException the io exception
     */
    public JsonArray(List<JsonValue> jsonValues) throws IOException {

        this.jsonValues = jsonValues;

    }

    /**
     * Gets json value.
     *
     * @param index the index
     * @return the json value
     */
    public JsonValue getJsonValue(int index) {

        JsonValue jsonValue = jsonValues.get(index);

        if (jsonValue == null)
        {
            return null;
        }

        return jsonValue;

    }

    /**
     * Helper method to return JsonValue of specific type
     * @param index List index
     * @param JsonType Type of expected JsonValue
     * @return JsonValue at the index
     */
    private JsonValue getJsonValue(int index, Class<? extends JsonValue> JsonType) {

        JsonValue jsonValue = getJsonValue(index);

        if (!(JsonType.isInstance(jsonValue)))
        {
            throw new JsonParseException("Requested key is not a " + jsonValue.getClass().getSimpleName() + ". It is a " + JsonType.getSimpleName());
        }

        return jsonValue;

    }

    /**
     * Gets json string.
     *
     * @param index the index
     * @return the json string
     */
    public JsonString getJSONString(int index) {

        return (JsonString) getJsonValue(index, JsonString.class);

    }

    /**
     * Gets string.
     *
     * @param index the index
     * @return the string
     */
    public String getString(int index) {

        return getJSONString(index).stringValue;

    }

    /**
     * Gets json number.
     *
     * @param index the index
     * @return the json number
     */
    public JsonNumber getJSONNumber(int index) {

        return (JsonNumber) getJsonValue(index, JsonNumber.class);

    }

    /**
     * Gets int.
     *
     * @param index the index
     * @return the int
     */
    public int getInt(int index) {

        return getJSONNumber(index).intValue();

    }

    /**
     * Gets json boolean.
     *
     * @param index the index
     * @return the json boolean
     */
    public JsonBoolean getJsonBoolean(int index) {

        return (JsonBoolean) getJsonValue(index, JsonBoolean.class);

    }

    /**
     * Gets boolean.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean getBoolean(int index) {

        return getJsonBoolean(index).booleanValue;

    }

    /**
     * Gets json object.
     *
     * @param index the index
     * @return the json object
     */
    public JsonObject getJsonObject(int index) {

        return (JsonObject) getJsonValue(index, JsonObject.class);

    }

    /**
     * Gets json array.
     *
     * @param index the index
     * @return the json array
     */
    public JsonArray getJsonArray(int index) {

        return (JsonArray) getJsonValue(index, JsonArray.class);

    }

    /**
     * Gets string list.
     *
     * @return the string list
     */
    public List<String> getStringList()
    {
        List<String> strings = new ArrayList<>();
        for (JsonValue jsonValue : jsonValues)
        {
            strings.add(jsonValue.toString());
        }

        return strings;
    }

    /**
     * To check what type of JsonValue this is.
     * @return the type of JsonValue
     */
    @Override
    public ValueType getJsonValueType() {
        return ValueType.JSON_ARRAY;
    }
}
