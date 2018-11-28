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

    public void parse() throws IOException
    {
        jsonSymbolList.add(next());
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
            while (Character.isWhitespace(c)) {
                c = reader.read();
            }
            reader.unread(c);
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
        else if (Character.isWhitespace(c)) {
            while (Character.isWhitespace(c)) {
                c = reader.read();
            }
            reader.unread(c);
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.SPACE);
        }
        else
        {
            jsonSymbol = new JSONSymbol(JSONSymbol.Type.OTHER, Character.toString((char) (c)));
        }

        return jsonSymbol;


    }


}
