package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonArray;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValueBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 13/12/2018
 * Time: 09:29
 */

public class JsonArrayParser {

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

            //JsonMember jsonMember = jsonMember(pushbackLexParser);
            if (symbol.type != JSONSymbol.Type.COMMA)
            {
                pushbackLexParser.unread(symbol);
                jsonValue = JsonValueBuilder.buildJsonValue(pushbackLexParser);
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
