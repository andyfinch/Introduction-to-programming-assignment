package uk.ac.uos.i2p.s193805.parser;

import javax.json.JsonException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 08/11/2018
 * Time: 09:17
 */

public class JSONToJavaParser {

    private String JSONString;

    /*private Map<String, String> jsonKeyValueMap = new HashMap<>();
    private Map<String, List<String>> jsonKeyArrayMap = new HashMap<>();
    private Map<String, JSONParser2> jsonKeyNestedObjectMap = new HashMap<>();

    public JSONParser2(String JSONString) {
        this.JSONString = JSONString;
    }*/

    public static JSONObject parseJSONtoJava(String jsonToParse)
    {
        String santisedJSON = jsonToParse.replace("\"", "").replace("\n", "").trim();

        char[] jsonToChar = santisedJSON.toCharArray();
        StringBuilder jsonKey = new StringBuilder();
        StringBuilder jsonValue = new StringBuilder();
        List<String> jsonArrayList = new ArrayList<>();

        boolean processingingKey = true;
        boolean processingingValue = false;
        boolean processingingArray = false;
        boolean processingingNested = false;

        int openBraces = 0;
        int closedBraces = 0;

        JSONObject jsonObject = new JSONObject();


        for (int i = 0; i < jsonToChar.length; i++)
        {
            char c = jsonToChar[i];
            System.out.print(c);

            if ( i == 0)
            {
                if (c != '{')
                {
                    throw new JsonException("Wrong JSON");
                }
                else
                {
                    continue;
                }
            }


            if ( processingingKey)
            {
                if ( c != ':' && c != ',')
                {
                    jsonKey.append(c);
                }

                if ( c == ':')
                {

                    processingingKey = false;
                    processingingValue = true;
                    continue;
                }
                /*if ( c == ',')
                {
                    processingingKey = false;
                }*/
            }

            if ( processingingValue && !processingingArray && !processingingNested )
            {
                if ( c == '{')
                {
                    jsonValue.append(c);
                    openBraces++;
                    processingingNested = true;
                    continue;
                }

                if (c == '[')
                {
                    processingingArray = true;
                    continue;
                }

                if ( c != ',' && c != '}')
                {
                   jsonValue.append(c);
                }
                else
                {
                    jsonObject.getJsonKeyValueMap().put(jsonKey.toString().trim(), jsonValue.toString().trim());
                    jsonKey = new StringBuilder();
                    jsonValue = new StringBuilder();
                    processingingValue = false;
                    processingingKey = true;
                }
            }
            
            if (processingingArray)
            {
                if (c != ',' && c != ']')
                {
                    jsonValue.append(c);
                }

                if (c == ',')
                {
                    jsonArrayList.add(jsonValue.toString().trim());
                    jsonValue = new StringBuilder();

                }
                if ( c == ']')
                {
                    jsonArrayList.add(jsonValue.toString().trim());
                    jsonObject.getJsonKeyArrayMap().put(jsonKey.toString().trim(), jsonArrayList);
                    jsonKey = new StringBuilder();
                    jsonValue = new StringBuilder();
                    processingingArray = false;
                    processingingValue = false;
                    processingingKey = true;
                }
            }



            if ( processingingNested)
            {
                if ( c == '{')
                {
                    openBraces++;
                }
                if ( c == '}')
                {
                    closedBraces++;
                }

                if ( openBraces != closedBraces)
                {
                    jsonValue.append(c);
                }
                else
                {
                    jsonValue.append(c);
                    JSONObject object2 = JSONToJavaParser.parseJSONtoJava(jsonValue.toString());
                    jsonObject.getJsonKeyNestedObjectMap().put(jsonKey.toString().trim(), object2);
                    jsonKey = new StringBuilder();
                    jsonValue = new StringBuilder();
                    processingingValue = false;
                    processingingNested = false;
                    processingingKey = true;
                }


            }

            


        }

        return jsonObject;

    }



}
