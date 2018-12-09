package uk.ac.uos.i2p.s193805.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LexParser {

    private List<JSONSymbol> jsonSymbolList  = new ArrayList<>();

    private final PushbackReader reader;

    public LexParser(Reader reader) {
        this.reader = new PushbackReader(reader);
    }


    public JSONSymbol next() throws IOException
    {
        int c = reader.read();


        if (c == -1)
        {
            return new JSONSymbol(JSONSymbol.Type.END);
        }
        else if (c == '{')
        {
            return new JSONSymbol(JSONSymbol.Type.OPEN_BRACE, "{");
        }
        else if (c == '"')
        {
            return new JSONSymbol(JSONSymbol.Type.QUOTE, "\"");

        }
        else if (Character.isLetter(c) || c == '/')
        {
            StringBuilder string = new StringBuilder();
            while (Character.isLetter(c) || c == ' ' || c == '/')
            {
                string.append((char)c);
                c = reader.read();
            }
            if (-1 != c)reader.unread(c);
            return new JSONSymbol(JSONSymbol.Type.STRING, string.toString());
        }
        else if (c == ':')
        {
            return new JSONSymbol(JSONSymbol.Type.COLON, ":");
        }
        else if (c == '-')
        {
            return new JSONSymbol(JSONSymbol.Type.MINUS_SIGN, "-");
        }
        else if (c == '+')
        {
            return new JSONSymbol(JSONSymbol.Type.PLUS_SIGN, "+");
        }
        else if (c == '.')
        {
            return new JSONSymbol(JSONSymbol.Type.DECIMAL_POINT, ".");
        }
        else if (Character.isDigit(c))
        {
            StringBuilder string = new StringBuilder();
            while (Character.isDigit(c))
            {
                string.append((char) c);
                c = reader.read();
            }
            if (-1 != c)reader.unread(c);
            return new JSONSymbol(JSONSymbol.Type.NUMBER, string.toString());
        }
        else if (c == ',')
        {
            return new JSONSymbol(JSONSymbol.Type.COMMA, ",");
        }
        else if (c == '[')
        {
            return new JSONSymbol(JSONSymbol.Type.OPEN_ARRAY, "[");
        }
        else if (c == ']')
        {
            return new JSONSymbol(JSONSymbol.Type.CLOSE_ARRAY, "]");
        }
        else if (c == '}')
        {
            return new JSONSymbol(JSONSymbol.Type.CLOSE_BRACE, "}");
        }
        else if (Character.isWhitespace(c)) {
            StringBuilder string = new StringBuilder();
            while (Character.isWhitespace(c)) {
                string.append((char)c);
                c = reader.read();
            }
            if (-1 != c)reader.unread(c);
            return new JSONSymbol(JSONSymbol.Type.SPACE, string.toString());
        }
        else
        {

            return new JSONSymbol(JSONSymbol.Type.OTHER, Character.toString((char) (c)));
        }

    }


}
