package uk.ac.uos.i2p.s193805.parser;

import uk.ac.uos.i2p.s193805.taskhandling.task.Tasks;

import javax.json.*;
import javax.json.stream.JsonParser;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 08:21
 */

public class JSONParser {

    private String jsonString;
    private JsonObject parsedJSONObject;

    public JSONParser(String jsonString) {
        this.jsonString = jsonString;
        this.jsonToObject(jsonString);

    }

    public JSONParser(JsonObject parsedJSONObject) {
        this.parsedJSONObject = parsedJSONObject;
    }

    private void jsonToObject(String json) {

        try (JsonReader jsonReader = Json.createReader(new StringReader(json)))
        {

            parsedJSONObject = jsonReader.readObject();

        }


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

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public JsonObject getParsedJSONObject() {
        return parsedJSONObject;
    }

    public void setParsedJSONObject(JsonObject parsedJSONObject) {
        this.parsedJSONObject = parsedJSONObject;
    }
}


/*public static void main(String[] args) {

        JsonObject jsonObject = null;
        try
        {
            //JsonParser jsonParser = Json.createParser(new StringReader("{\"id\": \"s113867\",\"tasks\": [\"/task/452359-4435382-6595137\",\"/task/99012-65325148-3574826\"]}"));

            InputStream fis = new FileInputStream(JSON_FILE);

            //create JsonReader object
            //JsonReader jsonReader = Json.createReader(fis);
            JsonReader jsonReader = Json.createReader(new StringReader(JSON_STRING));
            jsonObject = jsonReader.readObject();

            //we can close IO resource and JsonReader now
            jsonReader.close();
            fis.close();

            Tasks tasks = new Tasks();
            tasks.setId(jsonObject.getString("id"));


            //reading arrays from json
            JsonArray jsonArray = jsonObject.getJsonArray("tasks");

            for (JsonValue value : jsonArray)
            {
                tasks.getTaskURLS().add(value.toString());
            }

            //reading inner object from json object
        *//*JsonObject innerJsonObject = jsonObject.getJsonObject("address");
        Address address = new Address();
        address.setStreet(innerJsonObject.getString("street"));
        address.setCity(innerJsonObject.getString("city"));
        address.setZipcode(innerJsonObject.getInt("zipcode"));
        emp.setAddress(address);*//*

            //print employee bean information
            System.out.println(tasks.toString());

        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }*/