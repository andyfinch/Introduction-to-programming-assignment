package uk.ac.uos.i2p.s193805.parser;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 08/11/2018
 * Time: 09:17
 */

public class JSONParser2 {

    private String JSONString;

    private Map<String, String> jsonKeyValueMap = new HashMap<>();
    private Map<String, List<String>> jsonKeyArrayMap = new HashMap<>();
    private Map<String, JSONParser2> jsonKeyNestedObjectMap = new HashMap<>();

    public JSONParser2(String JSONString) {
        this.JSONString = JSONString;
    }

    public void parseJSON() throws Exception
    {
        String santisedJSON = JSONString.replace("\"", "").replace("\n", "");
        Scanner sc = new Scanner(santisedJSON);

        char[] jsonToChar = santisedJSON.toCharArray();
        StringBuilder jsonKey = new StringBuilder();
        StringBuilder jsonValue = new StringBuilder();

        boolean processingingKey = true;
        boolean processingingValue = false;
        boolean processingingArray = false;
        boolean processingingNested = false;
        List<String> jsonArrayList = new ArrayList<>();


        for (int i = 0; i < jsonToChar.length; i++)
        {
            char c = jsonToChar[i];
            System.out.print(c);

            if ( i == 0)
            {
                if (c != '{')
                {
                    throw new Exception("Wrong JSON");
                }
                else
                {
                    continue;
                }
            }

            /*if ( processingingKey )
            {
                if ( isWritableChar(c))
                {
                    jsonKey.append(c);
                }

                if ( jsonToChar[i+1] == ':')
                {
                    processingingKey = false;

                    if ( jsonToChar[i+2] == '[')
                    {
                        processingingArray = true;
                    }
                    else if ( jsonToChar[i+2] == '{')
                    {
                        processingingNested = true;
                    }
                    else
                    {
                        processingingValue = true;
                        continue;
                    }

                }
            }

            if ( processingingValue && !processingingArray && !processingingNested)
            {
                if ( isWritableChar(c))
                {
                    jsonValue.append(c);
                }


                if (i < jsonToChar.length-1)
                {
                    if (jsonToChar[i + 1] == ',')
                    {
                        jsonKeyValueMap.put(jsonKey.toString().trim(), jsonValue.toString().trim());
                        jsonKey = new StringBuilder();
                        jsonValue = new StringBuilder();
                        processingingValue = false;
                        processingingKey = true;
                        continue;
                    }
                }


            }*/

            if ( processingingKey)
            {
                if ( c != ':')
                {
                    jsonKey.append(c);
                }

                if ( c == ':')
                {

                    processingingKey = false;
                    processingingValue = true;
                    continue;
                }
                if ( c == ',')
                {
                    processingingKey = false;
                }
            }

            if ( processingingValue && !processingingArray )
            {
                if (c == '[')
                {
                    processingingArray = true;
                    continue;
                }

                if ( c != ',')
                {
                   jsonValue.append(c);
                }
                else
                {
                    jsonKeyValueMap.put(jsonKey.toString().trim(), jsonValue.toString().trim());
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
                    jsonKeyArrayMap.put(jsonKey.toString().trim(), jsonArrayList);
                    jsonKey = new StringBuilder();
                    jsonValue = new StringBuilder();
                    processingingArray = false;
                    processingingKey = true;
                }
            }

            


        }




        /*while (sc.hasNext())
        {
            String temp = sc.next();
            char[] temparr = temp.toCharArray();
            for (Character j : temparr)
            {
                System.out.print(j);
            }
        }*/
    }

    public String test(String json)
    {
        String removeQuotesAndNewLines = json.replace("\n", "");
        String [] temp = removeQuotesAndNewLines.split("\"");

        return null;

    }

    

    private boolean isWritableChar(char charToTest)
    {
        if ( charToTest == ':'
                || charToTest == ','
                || charToTest == '['
                || charToTest == ']'
                || charToTest == '{'
                || charToTest == '}')
        {
            return false;
        }

        return true;
    }

    public Map<String, String> getJsonKeyValueMap() {
        return jsonKeyValueMap;
    }

    public void setJsonKeyValueMap(Map<String, String> jsonKeyValueMap) {
        this.jsonKeyValueMap = jsonKeyValueMap;
    }
}
