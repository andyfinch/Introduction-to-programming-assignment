package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonArray;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonParsable;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValueBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonArrayParser implements JsonParsable {

    public final PushbackLexParser pushbackLexParser;

    public JsonArrayParser(PushbackLexParser pushbackLexParser) {

        this.pushbackLexParser = pushbackLexParser;

    }

    public JsonArray parse() throws IOException
    {
        JsonValue jsonValue;
        List<JsonValue> jsonValues = new ArrayList<>();
        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();

        if (symbol.type == JSONSymbol.Type.COMMA)
        {
            throw new JsonParseException("Array cannot start with ,");
        }
        pushbackLexParser.unread(symbol);

        while ((symbol = pushbackLexParser.nextSkipSpaces()).type != JSONSymbol.Type.CLOSE_ARRAY)
        {
            if (symbol.type == JSONSymbol.Type.CLOSE_BRACE)
            {
                continue;
            }

            if (symbol.type != JSONSymbol.Type.COMMA)
            {
                pushbackLexParser.unread(symbol);
                jsonValue = new JsonValueBuilder(pushbackLexParser).parse();
                jsonValues.add(jsonValue);
            }
            else
            {
                symbol = pushbackLexParser.nextSkipSpaces();
                if (symbol.type == JSONSymbol.Type.CLOSE_ARRAY)
                {
                    throw new JsonParseException("Trailing commas not allowed");
                }
                pushbackLexParser.unread(symbol);
            }

        }

        return new JsonArray(jsonValues);
    }

}
