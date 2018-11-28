package uk.ac.uos.i2p.s193805.parser;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 28/11/2018
 * Time: 09:10
 */

public class JSONToJavaParser2 {

    private final Reader reader;
    private JSONObject jsonObject = new JSONObject();
    private char currentChar;
    private JSONSymbol currentJSONSymbol;
    private State state;
    private String jsonKey;
    private String jsonValue;

    public enum State {
        KEY,
        STING_VALUE,
        ARRAY,
        NESTED

    }

    public JSONToJavaParser2(Reader reader) {
        this.reader = reader;
    }

    public void parse() throws IOException
    {
        JSONSymbol.Type jsonSymbolType;

        //validate starts with open brace
        eatWhiteSpace();
        if (currentJSONSymbol.type != JSONSymbol.Type.OPEN_BRACE)
        {
            throw new RuntimeException("JSON Must start with {");
        }
        else
        {
            state = State.KEY;
        }

        while (next().type != JSONSymbol.Type.END)
        {
            if ( state == State.KEY)
            {
                eatWhiteSpace();
                jsonKey();
            }

            if (state == State.STING_VALUE)
            {
                eatWhiteSpace();
                jsonValue();
            }
        }






    }

    public String jsonKey() throws IOException
    {
        jsonKey = getStringLiteral();
        jsonObject.getJsonKeyValueMap().put(jsonKey, null);

        //Now check next char is a colon
        eatWhiteSpace();
        if (currentJSONSymbol.type != JSONSymbol.Type.COLON)
        {
            throw new RuntimeException("JSON key must be followed by a Colon");
        }
        else
        {
            state = State.STING_VALUE;
        }


        return jsonKey;

    }

    public String jsonValue() throws IOException {
        jsonValue = getStringLiteral();
        jsonObject.getJsonKeyValueMap().put(jsonKey, jsonValue);

        //Now check next char is a ,
        eatWhiteSpace();
        eatNewLines();
        if (currentJSONSymbol.type != JSONSymbol.Type.COMMA)
        {
            throw new RuntimeException("JSON value must be followed by a Comma");
        }
        else
        {
            state = State.KEY;
        }


        return jsonValue;

    }

    private void eatWhiteSpace() throws IOException
    {
        while ((next().type) == JSONSymbol.Type.SPACE)
        {
            System.out.println("eating white space");
        }
    }

    private void eatNewLines() throws IOException {
        while ((next().type) == JSONSymbol.Type.NEW_LINE)
        {
            System.out.println("eating new lines");
        }
    }

    public String getStringLiteral() throws IOException
    {
        StringBuilder key = new StringBuilder();

        while (next().type != JSONSymbol.Type.QUOTE)
        {
            key.append(currentChar);
        }

        System.out.println(key.toString());
        return key.toString();
    }

    public JSONSymbol next() throws IOException
    {
        int c = reader.read();
        currentChar = (char)c;
        JSONSymbol jsonSymbol;


        if (c == -1)
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.END);
        }
        else if (c == '{')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.OPEN_BRACE);
        }
        else if (c == '}')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.CLOSE_BRACE);
        }
        else if (c == '[')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.OPEN_ARRAY);
        }
        else if (c == ']')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.CLOSE_ARRAY);
        }
        else if (c == '"')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.QUOTE);
        }
        else if (c == ':')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.COLON);
        }
        else if (c == '\n')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.NEW_LINE);
        }
        else if (c == ',')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.COMMA);
        }
        else if (Character.isWhitespace(c))
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.SPACE);
        }
        else if ( Character.isLetterOrDigit(c))
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.STRING_VALUE, Character.toString((char) (c)));
        }
        else
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.OTHER, Character.toString((char) (c)));
        }

        currentJSONSymbol = jsonSymbol;
        return jsonSymbol;


    }
}
