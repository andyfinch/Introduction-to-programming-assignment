package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonParsable;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonString;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.END;
import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.QUOTE;


/**
 * Attempts to pass a JSON String
 */
public class JsonStringParser implements JsonParsable {

    /**
     * The Pushback lex parser.
     */
    public final PushbackLexParser pushbackLexParser;

    /**
     * Instantiates a new Json string parser.
     *
     * @param pushbackLexParser the pushback lex parser
     */
    public JsonStringParser(PushbackLexParser pushbackLexParser) {

        this.pushbackLexParser = pushbackLexParser;

    }

    /**
     * Attempts to parse a JSON String and convert to JsonString object
     * @return new JsonString object
     * @throws IOException
     */
    public JsonString parse() throws IOException
    {
        StringBuilder value = new StringBuilder();
        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();

        if ( symbol.type != QUOTE)
        {
            throw new JsonParseException("Json string must start with \"");
        }

        while ((symbol = pushbackLexParser.nextSkipSpaces()).type != QUOTE)
        {
            if (symbol.type == END)
            {
                throw new JsonParseException("Json string must end with \"");
            }
            value.append(symbol.value);
        }

        return new JsonString(value.toString());

    }
}
