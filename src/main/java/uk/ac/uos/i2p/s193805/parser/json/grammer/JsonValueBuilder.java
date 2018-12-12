package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 10/12/2018
 * Time: 16:18
 */

public class JsonValueBuilder {

    public static JsonValue buildJsonValue(PushbackLexParser pushbackLexParser) throws IOException
    {

        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();

        if (END == symbol.type) return null;

        if (symbol.type == QUOTE)
        {
            StringBuilder value = new StringBuilder();
            while ((symbol = pushbackLexParser.nextSkipSpaces()).type != QUOTE)
            {
                if (symbol.type == END)
                {
                    throw new JsonParseException("Quoted Value must end with \"");
                }
                value.append(symbol.value);
            }

            return new JsonString(value.toString());

        }
        else if (symbol.type == NUMBER || symbol.type == MINUS_SIGN)
        {
            pushbackLexParser.unread(symbol);
            return new JsonNumberParser(pushbackLexParser).parse();
        }
        else if (symbol.type == STRING)
        {
            if (symbol.value.equals("true") || symbol.value.equals("false"))
            {
                return new JsonBoolean(Boolean.valueOf(symbol.value));
            }
            else if (symbol.value.equals("null"))
            {
                return null;
            }
            else
            {
                throw new JsonParseException("Unquoted String value not allowed");
            }

        }
        else if (symbol.type == OPEN_BRACE)
        {
            return new JsonObject(pushbackLexParser);
        }
        else if (symbol.type == OPEN_ARRAY)
        {
            return new JsonArray(pushbackLexParser);
        }

        return null;


    }
}
