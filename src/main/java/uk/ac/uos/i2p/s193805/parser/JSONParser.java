package uk.ac.uos.i2p.s193805.parser;

import uk.ac.uos.i2p.s193805.taskhandling.task.Tasks;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParsingException;
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


    public static JavaJSONObject parseJSONtoJava(String json)
    {
        JavaJSONObject javaJSONObject;

        try (JsonReader jsonReader = Json.createReader(new StringReader(json)))
        {

            try
            {
                JsonObject parsedJSONObject = jsonReader.readObject();
                javaJSONObject = new JavaJSONObject(parsedJSONObject);

            } catch (JsonParsingException e)
            {
                throw new JsonParsingException("JSON is invalid " + e.getMessage(), e.getLocation());
            }

        }

        return javaJSONObject;

    }

}


