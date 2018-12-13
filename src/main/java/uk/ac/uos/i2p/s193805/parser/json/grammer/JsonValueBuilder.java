package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers.JsonArrayParser;
import uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers.JsonNumberParser;
import uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers.JsonObjectParser;
import uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers.JsonStringParser;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 10/12/2018
 * Time: 16:18
 */

public class JsonValueBuilder {

    private PushbackLexParser pushbackLexParser;

    public JsonValueBuilder(PushbackLexParser pushbackLexParser) {
        this.pushbackLexParser = pushbackLexParser;
    }

    public JsonValue parse() throws IOException
    {

        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();

        if (END == symbol.type) return null;

        if (symbol.type == QUOTE)
        {
            return new JsonStringParser(pushbackLexParser).parse();

        }
        else if (symbol.type == NUMBER || symbol.type == MINUS_SIGN)
        {
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
            return new JsonObjectParser(pushbackLexParser).parse();
        }
        else if (symbol.type == OPEN_ARRAY)
        {
            return new JsonArrayParser(pushbackLexParser).parse();
        }

        return null;


    }
}
