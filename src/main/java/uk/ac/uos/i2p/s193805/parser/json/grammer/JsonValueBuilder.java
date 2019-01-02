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
 * Helper class to process JSON string and pass to correct Json parser
 */
public class JsonValueBuilder implements JsonParsable {

    private PushbackLexParser pushbackLexParser;

    /**
     * Instantiates a new Json value builder.
     *
     * @param pushbackLexParser the pushback lex parser
     */
    public JsonValueBuilder(PushbackLexParser pushbackLexParser) {
        this.pushbackLexParser = pushbackLexParser;
    }

    public JsonValue parse() throws IOException
    {

        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();

        if (END == symbol.type) return null;

        if (symbol.type == QUOTE) //Assume we are processing a quoted string
        {
            pushbackLexParser.unread(symbol);
            return new JsonStringParser(pushbackLexParser).parse();

        }
        else if (symbol.type == NUMBER || symbol.type == MINUS_SIGN) //Assume we are processing a number
        {
            pushbackLexParser.unread(symbol);
            return new JsonNumberParser(pushbackLexParser).parse();
        }
        else if (symbol.type == STRING) //Assume we are processing boolean or null value
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
        else if (symbol.type == OPEN_BRACE) //Assume we are processing a new JSON object
        {
            pushbackLexParser.unread(symbol);
            return new JsonObjectParser(pushbackLexParser).parse();
        }
        else if (symbol.type == OPEN_ARRAY) //Assume we are processing an Array
        {
            return new JsonArrayParser(pushbackLexParser).parse();
        }
        else
        {
            throw new JsonParseException("Unable to parse JSON " + symbol + " is not valid");
        }


    }
}
