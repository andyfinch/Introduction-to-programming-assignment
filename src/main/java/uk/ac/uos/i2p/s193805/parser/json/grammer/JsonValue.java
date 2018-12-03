package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

public class JsonValue {

    public final Object jsonValue;

    public JsonValue(PushbackLexParser pushbackLexParser) throws IOException {

        this.jsonValue = value(pushbackLexParser);
    }

    private Object value(PushbackLexParser lex) throws IOException {
        JSONSymbol symbol = lex.nextSkipSpaces();


        if (JSONSymbol.Type.END == symbol.type) return null;

        if (symbol.type == JSONSymbol.Type.QUOTE)
        {
            StringBuilder value = new StringBuilder();
            while ((symbol = lex.nextSkipSpaces()).type != JSONSymbol.Type.QUOTE)
            {
                if (symbol.type == JSONSymbol.Type.END)
                {
                    throw new RuntimeException("Quoted Value must end with \"");
                }
                value.append(symbol.value);
            }

            return value.toString();

        }
        else if ( symbol.type == NUMBER)
        {
            return Integer.valueOf(symbol.value);
        }
        else if (symbol.type == STRING)
        {
            if ( symbol.value.equals("true") || symbol.value.equals("false") )
            {
                return Boolean.valueOf(symbol.value);
            }
            else if (symbol.value.equals("null"))
            {
                return null;
            }
            else
            {
                throw new RuntimeException("Unquoted String value not allowed");
            }

        }

        return null;
    }


}
