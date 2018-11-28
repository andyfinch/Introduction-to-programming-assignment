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

    public JSONToJavaParser2(Reader reader) {
        this.reader = reader;
    }

    public void parse() throws IOException
    {
        JSONSymbol.Type jsonSymbolType;

        while ((jsonSymbolType = next().type) != JSONSymbol.Type.END)
        {
            switch (jsonSymbolType)
            {
                case QUOTE:
                    jsonKey();
            }
        }

    }

    public String jsonKey() throws IOException
    {
        StringBuilder key = new StringBuilder(currentChar);

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

        if (c == -1)
        {
            return new JSONSymbol(JSONSymbol.Type.END);
        }
        else if (c == '{')
        {
            return new JSONSymbol(JSONSymbol.Type.OPEN_BRACE);
        }
        else if (c == '}')
        {
            return new JSONSymbol(JSONSymbol.Type.CLOSE_BRACE);
        }
        else if (c == '[')
        {
            return new JSONSymbol(JSONSymbol.Type.OPEN_ARRAY);
        }
        else if (c == ']')
        {
            return new JSONSymbol(JSONSymbol.Type.CLOSE_ARRAY);
        }
        else if (c == '"')
        {
            return new JSONSymbol(JSONSymbol.Type.QUOTE);
        }
        else if (c == ':')
        {
            return new JSONSymbol(JSONSymbol.Type.COLON);
        }
        else if (c == '\n')
        {
            return new JSONSymbol(JSONSymbol.Type.NEW_LINE);
        }
        else if (Character.isWhitespace(c))
        {
            return new JSONSymbol(JSONSymbol.Type.SPACE);
        }
        else if ( Character.isLetterOrDigit(c))
        {
            return new JSONSymbol(JSONSymbol.Type.STRING_VALUE, Character.toString((char) (c)));
        }

        return new JSONSymbol(JSONSymbol.Type.OTHER, Character.toString((char)(c)));

    }
}
