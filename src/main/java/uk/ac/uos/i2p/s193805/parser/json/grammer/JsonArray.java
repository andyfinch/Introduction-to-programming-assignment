package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonArray implements JsonValue {

    public final List<JsonValue> jsonValues = new ArrayList<>();

    public JsonArray(PushbackLexParser pushbackLexParser) throws IOException {

        JsonValue jsonValue;
        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();

        if (symbol.type == JSONSymbol.Type.COMMA)
        {
            throw new JsonParseException("Array cannot start with ,");
        }
        pushbackLexParser.unread(symbol);

        while ((symbol = pushbackLexParser.nextSkipSpaces()).type != JSONSymbol.Type.CLOSE_ARRAY)
        {
            if (symbol.type == JSONSymbol.Type.CLOSE_BRACE)
            {
                continue;
            }

            //JsonMember jsonMember = jsonMember(pushbackLexParser);
            if ( symbol.type != JSONSymbol.Type.COMMA)
            {
                pushbackLexParser.unread(symbol);
                jsonValue = JsonValueBuilder.buildJsonValue(pushbackLexParser);
                jsonValues.add(jsonValue);
            }
            else
            {
                symbol = pushbackLexParser.nextSkipSpaces();
                if ( symbol.type == JSONSymbol.Type.CLOSE_ARRAY)
                {
                    throw new JsonParseException("Trailing commas not allowed");
                }
                pushbackLexParser.unread(symbol);
            }

        }



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
}
