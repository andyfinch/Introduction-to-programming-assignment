package uk.ac.uos.i2p.s193805.parser;

import javax.json.*;
import javax.json.stream.JsonParsingException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 08:21
 */

public class JavaJSONObject {

    private JsonObject parsedJSONObject;

    public JavaJSONObject(JsonObject parsedJSONObject) {
        this.parsedJSONObject = parsedJSONObject;
    }


    public String getJSONStringValue(String keyName) throws IllegalArgumentException
    {
        checkKeyExists(keyName);

        return parsedJSONObject.getString(keyName);
    }

    public int getJSONIntValue(String keyName) {

        checkKeyExists(keyName);

        return parsedJSONObject.getInt(keyName);
    }

    public List<Object> getJSONArray(String keyName) {

        checkKeyExists(keyName);

        //reading arrays from json
        JsonArray jsonArray = parsedJSONObject.getJsonArray(keyName);
        List<Object> jsonValues = new ArrayList<>();

        for (JsonValue jsonValue : jsonArray)
        {

            if ( jsonValue.getValueType() == JsonValue.ValueType.STRING)
            {
                jsonValues.add(((JsonString) jsonValue).getString());
            }
            if (jsonValue.getValueType() == JsonValue.ValueType.NUMBER)
            {
                jsonValues.add(((JsonNumber) jsonValue).bigIntegerValue().intValue());
            }


        }

        return jsonValues;

    }

    public List<String> getJSONStringArray(String keyName) {

        checkKeyExists(keyName);

        //reading arrays from json
        JsonArray jsonArray = parsedJSONObject.getJsonArray(keyName);
        List<String> jsonValues = new ArrayList<>();

        for (JsonValue jsonValue : jsonArray)
        {

            jsonValues.add(((JsonString) jsonValue).getString());

        }

        return jsonValues;

    }

    public Object getNestedJSONKey(String finalKey, String ...groupNames) {


        JsonObject jsonObject = null;

        for (int i = 0; i < groupNames.length; i++)
        {
            String key = groupNames[i];


            if ( i == 0 )
            {
                checkKeyExists(key);
                jsonObject = parsedJSONObject.getJsonObject(key);

            }
            else
            {
                checkKeyExists(jsonObject, key);
                jsonObject = jsonObject.getJsonObject(key);
            }

        }


        return jsonObject.getString(finalKey);
    }

    public JsonObject getNestedJSONObject(String keyName) {

        checkKeyExists(keyName);

        return parsedJSONObject.getJsonObject(keyName);
    }


    private void checkKeyExists(String keyName){
        if (parsedJSONObject.get(keyName) == null)
        {
            throw new IllegalArgumentException("JSON key does not exist");
        }

    }

    private void checkKeyExists(JsonObject jsonObject, String keyName) {
        if (jsonObject.get(keyName) == null)
        {
            throw new IllegalArgumentException("JSON key does not exist");
        }

    }


    public JsonObject getParsedJSONObject() {
        return parsedJSONObject;
    }

    public void setParsedJSONObject(JsonObject parsedJSONObject) {
        this.parsedJSONObject = parsedJSONObject;
    }
}


