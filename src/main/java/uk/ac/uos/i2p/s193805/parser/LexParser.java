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
        JSONSymbol jsonSymbol;


        if (c == -1)
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.END);
        }
        else if (c == '{')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.OPEN_BRACE, "{");
        }
        else if (c == '"')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.QUOTE, "\"");

        }
        else if (Character.isLetter(c))
        {
            StringBuilder string = new StringBuilder();
            while (Character.isLetter(c))
            {
                string.append((char)c);
                c = reader.read();
            }
            reader.unread(c);
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.STRING, string.toString());
        }
        else if (c == ':')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.COLON, ":");
        }
        else if (Character.isDigit(c))
        {
            StringBuilder string = new StringBuilder();
            while (Character.isDigit(c))
            {
                string.append((char) c);
                c = reader.read();
            }
            reader.unread(c);
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.NUMBER, string.toString());
        }
        else if (c == ',')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.COMMA, ",");
        }
        else if (c == '[')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.OPEN_ARRAY, "[");
        }
        else if (c == ']')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.CLOSE_ARRAY, "]");
        }
        else if (c == '}')
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.CLOSE_BRACE, "}");
        }
        else if (Character.isWhitespace(c)) {
            StringBuilder string = new StringBuilder();
            while (Character.isWhitespace(c)) {
                string.append((char)c);
                c = reader.read();
            }
            reader.unread(c);
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.SPACE, string.toString());
        }
        else
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.OTHER, Character.toString((char) (c)));
        }

        return jsonSymbol;


    }


}