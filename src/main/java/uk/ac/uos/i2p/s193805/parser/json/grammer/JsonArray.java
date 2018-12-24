package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonArray implements JsonValue {

    public final List<JsonValue> jsonValues;

    public JsonArray(List<JsonValue> jsonValues) throws IOException {

        this.jsonValues = jsonValues;

    }

    public JsonValue getJsonValue(int index) {

        JsonValue jsonValue = jsonValues.get(index);

        if (jsonValue == null)
        {
            return null;
        }

        return jsonValue;

    }

    private JsonValue getJsonValue(int index, Class<? extends JsonValue> JsonType) {

        JsonValue jsonValue = getJsonValue(index);

        if (!(JsonType.isInstance(jsonValue)))
        {
            throw new JsonParseException("Requested key is not a " + jsonValue.getClass().getSimpleName() + ". It is a " + JsonType.getSimpleName());
        }

        return jsonValue;

    }

    public JsonString getJSONString(int index) {

        return (JsonString) getJsonValue(index, JsonString.class);

    }

    public String getString(int index) {

        return getJSONString(index).stringValue;

    }

    public JsonNumber getJSONNumber(int index) {

        return (JsonNumber) getJsonValue(index, JsonNumber.class);

    }

    public int getInt(int index) {

        return getJSONNumber(index).intValue();

    }

    public JsonBoolean getJsonBoolean(int index) {

        return (JsonBoolean) getJsonValue(index, JsonBoolean.class);

    }

    public boolean getBoolean(int index) {

        return getJsonBoolean(index).booleanValue;

    }

    public JsonObject getJsonObject(int index) {

        return (JsonObject) getJsonValue(index, JsonObject.class);

    }

    public JsonArray getJsonArray(int index) {

        return (JsonArray) getJsonValue(index, JsonArray.class);

    }

    public List<String> getStringList()
    {
        List<String> strings = new ArrayList<>();
        for (JsonValue jsonValue : jsonValues)
        {
            strings.add(jsonValue.toString());
        }

        return strings;
    }

    @Override
    public ValueType getJsonValueType() {
        return ValueType.JSON_ARRAY;
    }
}
