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

    public static JSONObject parseJSONtoJava(String jsonToParse) {
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

            if (i == 0)
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


            if (processingingKey)
            {
                if (c != ':' && c != ',')
                {
                    jsonKey.append(c);
                }

                if (c == ':')
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

            if (processingingValue && !processingingArray && !processingingNested)
            {
                if (c == '{')
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

                if (c != ',' && c != '}')
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
                if (c == ']')
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


            if (processingingNested)
            {
                if (c == '{')
                {
                    openBraces++;
                }
                if (c == '}')
                {
                    closedBraces++;
                }

                if (openBraces != closedBraces)
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

    public static boolean validateJSONString(String jsonToValidate) {
        String santisedJSONforValidation = jsonToValidate.replace(" ", "").replace("\n", "").trim();

        char[] jsonCharArray = santisedJSONforValidation.toCharArray();

        boolean openBraceFound = false;
        boolean closedBraceFound = false;

        boolean firstQuoteFound = false;
        boolean matchedQuoteFound = false;

        boolean processingKey = true;
        boolean processingValue = false;

        boolean processingArray = false;
        boolean processingNested = false;
        boolean firstArrayBracketFound = false;
        boolean matchedArrayBracketFound = false;

        StringBuilder stringBuilder = new StringBuilder();

        boolean validJSON = true;

        for (int i = 0; i < jsonCharArray.length; i++)
        {
            char c = jsonCharArray[i];
            stringBuilder.append(c);

            if (i == 0 && c != '{')
            {
                throw new RuntimeException("Missing open brace");
            }
            if (i == jsonCharArray.length - 1 && c != '}')
            {
                throw new RuntimeException("Missing closing brace");
            }

            if (processingKey && !processingArray)
            {

                if (c == ']')
                {
                    throw new RuntimeException("Array missing [");
                }
                
                if (!firstQuoteFound && c == '"')
                {
                    firstQuoteFound = true;
                    continue;
                }

                if (firstQuoteFound /*&& !matchedQuoteFound*/)
                {
                    if (c == '"')
                    {
                        firstQuoteFound = false;

                        if ( jsonCharArray[i+1] != ':')
                        {
                            throw new RuntimeException("Missing Colon");
                        }
                        else if ( jsonCharArray[i+2] == '[' )
                        {
                            processingKey = false;
                            processingValue = false;
                            processingArray = true;
                            i+=1;
                        }
                        else if (jsonCharArray[i+2] == '{')
                        {
                            processingKey = false;
                            processingValue = false;
                            processingArray = false;
                            processingNested = true;
                            i +=1;
                        }
                        else if (jsonCharArray[i + 2] == ',' || jsonCharArray[i + 2] == '}')
                        {
                            throw new RuntimeException("Missing Value");
                        }
                        else
                        {
                            processingKey = false;
                            processingValue = true;
                            i += 1;
                        }

                        continue;
                    }

                }

            }

            if ( processingValue)
            {

                if ( firstQuoteFound && c == '"')
                {
                    matchedQuoteFound = true;
                    continue;
                    //2nd quote found
                }

                if ( !firstQuoteFound && c == '"')
                {

                    firstQuoteFound = true;
                    continue;
                }

                if ( !firstQuoteFound && c != '"' ) //should be number then
                {
                    try
                    {
                        if ( processingArray && c == ',')
                        {
                            continue;
                        }

                        Integer.parseInt(String.valueOf(c));

                        if ( jsonCharArray[i+1] == ',' || jsonCharArray[i + 1] == '}')
                        {
                            processingValue = false;
                            processingKey = true;
                            firstQuoteFound = false;
                            matchedQuoteFound = false;

                        }

                    } catch (NumberFormatException e)
                    {
                        throw new RuntimeException("String value not quoted");
                    }

                }

                if (firstQuoteFound && !matchedQuoteFound && (c == '}' || c == ','))
                {
                    throw new RuntimeException("String value end quote missing");

                }


                if ( c == ',')
                {
                    processingValue = false;
                    processingKey = true;
                    firstQuoteFound = false;
                    matchedQuoteFound = false;
                }


                /*if ( firstQuoteFound && c == '"')
                {
                    if ( jsonCharArray[i+1] != ',')
                    {
                        throw new RuntimeException("X");
                    }
                }*/
            }

            if ( processingArray  )
            {
                if ( c== '[')
                {
                    continue;
                }

                if ( c== ']')
                {
                    processingValue = false;
                    processingKey = true;
                    firstQuoteFound = false;
                    matchedQuoteFound = false;
                    processingArray = false;
                    continue;
                }

                if (firstQuoteFound && c == '"')
                {
                    matchedQuoteFound = true;
                    continue;
                    //2nd quote found
                }

                if (!firstQuoteFound && c == '"')
                {

                    firstQuoteFound = true;
                    continue;
                }

                if (!firstQuoteFound && c != '"') //should be number then
                {
                    try
                    {
                        if (c == ',')
                        {
                            firstQuoteFound = false;
                            matchedQuoteFound = false;
                            continue;
                        }

                        Integer.parseInt(String.valueOf(c));

                        if (jsonCharArray[i + 1] == ',' || jsonCharArray[i + 1] == '}')
                        {
                            processingValue = false;
                            processingKey = true;
                            firstQuoteFound = false;
                            matchedQuoteFound = false;

                        }

                    } catch (NumberFormatException e)
                    {
                        throw new RuntimeException("String value not quoted");
                    }

                }

                if (firstQuoteFound && !matchedQuoteFound && (c == '}' || c == ','))
                {
                    throw new RuntimeException("String value end quote missing");

                }


                if (c == ',')
                {
                    processingValue = false;
                    processingKey = true;
                    firstQuoteFound = false;
                    matchedQuoteFound = false;
                }
            }


        }


        System.out.println(santisedJSONforValidation);

        return false;
    }

}
