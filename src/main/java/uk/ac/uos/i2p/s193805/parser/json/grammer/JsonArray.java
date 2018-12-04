package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonArray {

    public final List<JsonValue> jsonValues = new ArrayList<>();

    public JsonArray(PushbackLexParser pushbackLexParser) throws IOException {

        JsonValue jsonValue;
        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();

        if (symbol.type == JSONSymbol.Type.COMMA)
        {
            throw new RuntimeException("Array cannot start with ,");
        }
        pushbackLexParser.unread(symbol);

        while ((symbol = pushbackLexParser.nextSkipSpaces()).type != JSONSymbol.Type.CLOSE_ARRAY)
        {
            //JsonMember jsonMember = jsonMember(pushbackLexParser);
            if ( symbol.type != JSONSymbol.Type.COMMA)
            {
                pushbackLexParser.unread(symbol);
                jsonValue = value(pushbackLexParser);
                jsonValues.add(jsonValue);
            }
            else
            {
                symbol = pushbackLexParser.nextSkipSpaces();
                if ( symbol.type == JSONSymbol.Type.CLOSE_ARRAY)
                {
                    throw new RuntimeException("Trailing commas not allowed");
                }
                pushbackLexParser.unread(symbol);
            }

        }



    }

    private JsonValue value(PushbackLexParser lex) throws IOException {
        /*JSONSymbol symbol = lex.next();

        if ( symbol.type != JSONSymbol.Type.COMMA)
        {
            lex.unread(symbol);
            return new JsonValue(lex);

        }



        return null;*/

        return new JsonValue(lex);


    }
}
