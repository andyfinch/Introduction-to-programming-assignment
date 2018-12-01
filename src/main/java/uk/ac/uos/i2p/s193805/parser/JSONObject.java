package uk.ac.uos.i2p.s193805.parser;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 08/11/2018
 * Time: 09:17
 */

public class JSONObject {

    private String JSONString;

    private Map<String, String> jsonKeyValueMap = new HashMap<>();
    private Map<String, List<String>> jsonKeyArrayMap = new HashMap<>();
    private Map<String, JSONObject> jsonKeyNestedObjectMap = new HashMap<>();


    public String getJSONStringValue(String keyName) throws IllegalArgumentException {

        String[] splitKeyName = keyName.split("\\.");

        if (splitKeyName.length == 1)
        {
            checkKeyExists(keyName);
        }
        else
        {
            JSONObject jsonObject = this;
            for (int i = 0; i < splitKeyName.length; i++)
            {
                String nestedKeyName = splitKeyName[i];


                if (i != splitKeyName.length - 1)//last one
                {
                    jsonObject = jsonObject.getNestedJSONObject(nestedKeyName);
                }
                else
                {
                    return jsonObject.getJSONStringValue(nestedKeyName);
                }


            }

        }

        return jsonKeyValueMap.get(keyName);
    }

    public int getJSONIntValue(String keyName) {

        checkKeyExists(keyName);
        int returnInt;
        try
        {
             returnInt = Integer.parseInt(jsonKeyValueMap.get(keyName));
        } catch (NumberFormatException e)
        {
            throw new NumberFormatException("JsonValue is not a Integer Type " + jsonKeyValueMap.get(keyName));
        }

        return returnInt;
    }

    public List<Integer> getJSONIntArray(String keyName) {

        if (jsonKeyArrayMap.get(keyName) == null)
        {
            throw new IllegalArgumentException("JSON key does not exist");
        }

        List<Integer> integerList = new ArrayList<>();

        for (String s : getJSONStringArray(keyName))
        {
            try
            {
                integerList.add(Integer.parseInt(s));
            } catch (NumberFormatException e)
            {
                throw new NumberFormatException("Array with key of " + keyName + " does not contain all Integers " + s);
            }
        }


        return integerList;

    }


    public List<String> getJSONStringArray(String keyName) {

        if (jsonKeyArrayMap.get(keyName) == null)
        {
            throw new IllegalArgumentException("JSON key does not exist");
        }

        return jsonKeyArrayMap.get(keyName);

    }

    public JSONObject getNestedJSONObject(String keyName)
    {
        if (jsonKeyNestedObjectMap.get(keyName) == null)
        {
            throw new IllegalArgumentException("JSON key does not exist");
        }

        return jsonKeyNestedObjectMap.get(keyName);
    }


    private void checkKeyExists(String keyName) {
        if (jsonKeyValueMap.get(keyName) == null)
        {
            throw new IllegalArgumentException("JSON key does not exist");
        }

    }


    public Map<String, String> getJsonKeyValueMap() {
        return jsonKeyValueMap;
    }

    public void setJsonKeyValueMap(Map<String, String> jsonKeyValueMap) {
        this.jsonKeyValueMap = jsonKeyValueMap;
    }

    public Map<String, List<String>> getJsonKeyArrayMap() {
        return jsonKeyArrayMap;
    }

    public void setJsonKeyArrayMap(Map<String, List<String>> jsonKeyArrayMap) {
        this.jsonKeyArrayMap = jsonKeyArrayMap;
    }

    public Map<String, JSONObject> getJsonKeyNestedObjectMap() {
        return jsonKeyNestedObjectMap;
    }

    public void setJsonKeyNestedObjectMap(Map<String, JSONObject> jsonKeyNestedObjectMap) {
        this.jsonKeyNestedObjectMap = jsonKeyNestedObjectMap;
    }
}
