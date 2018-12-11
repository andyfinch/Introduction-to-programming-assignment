package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

public class JSON {

    public final JsonObject jsonObject;

    public JSON(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSON(PushbackLexParser pushbackLexParser) throws IOException {
        this.jsonObject = jsonObject(pushbackLexParser);
    }

    private JsonObject jsonObject(PushbackLexParser lexParser) throws IOException
    {
        JSONSymbol symbol = lexParser.nextSkipSpaces();

        if ( symbol.type != OPEN_BRACE)
        {
            throw new RuntimeException("JSON Object must start with {");
        }

        JsonObject jsonObject = new JsonObject(lexParser);

        return jsonObject;

    }

    /*private JsonMember jsonMember(PushbackLexParser lexParser) throws IOException {
        String key = key(lexParser);
        if (null == key)
        {
            throw new RuntimeException("Json member must have string key");
        }
        JsonValue value = value(lexParser);
        if (null == value)
        {
            throw new RuntimeException("Json member must have value");
        }

        return new JsonMember(key, value);
    }*/

    /*private String key(PushbackLexParser lex) throws IOException {
        JSONSymbol symbol = lex.next();
        if (JSONSymbol.Type.END == symbol.type) return null;

        if (symbol.type != JSONSymbol.Type.QUOTE) return null;

        symbol = lex.next();
        if (symbol.type != JSONSymbol.Type.STRING) {
            throw new IOException("Expected STRING, got " + symbol.type);
        }
        String key = symbol.value;

        symbol = lex.next();
        if (symbol.type != JSONSymbol.Type.QUOTE) {
            throw new IOException("Expected \", got " + symbol.type);
        }

        symbol = lex.next();
        if ( symbol.type == SPACE)
        {
            symbol = lex.next();
        }

        if (symbol.type != COLON)
        {
            throw new RuntimeException("Key must be followed by :");
        }

        return key;
    }*/

    /*private JsonValue value(PushbackLexParser lex) throws IOException {
        JSONSymbol symbol = lex.next();
        if (JSONSymbol.Type.END == symbol.type)
        {
            throw new IOException("Expected VALUE, got " + symbol.type);
        }

        return new JsonValue(lex);
    }*/


}
