package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonString;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.END;
import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.QUOTE;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 13/12/2018
 * Time: 09:19
 */

public class JsonStringParser {

    public final PushbackLexParser pushbackLexParser;

    public JsonStringParser(PushbackLexParser pushbackLexParser) {

        this.pushbackLexParser = pushbackLexParser;

    }

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

        //pushbackLexParser.unread(symbol);
        return new JsonString(value.toString());

    }
}
