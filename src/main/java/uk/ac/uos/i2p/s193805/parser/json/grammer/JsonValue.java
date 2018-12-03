package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.COLON;
import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.SPACE;

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
            symbol = lex.next();

            if (symbol.type == JSONSymbol.Type.STRING)
            {
                String value = symbol.value;
                symbol = lex.next();
                if (symbol.type != JSONSymbol.Type.QUOTE)
                {
                    throw new RuntimeException("JSON String value end with \"");
                }
                return value;
            }
            else if (symbol.type == JSONSymbol.Type.QUOTE )
            {
                return "";
            }


        }

        return null;
    }


}
