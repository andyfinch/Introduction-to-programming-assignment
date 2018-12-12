package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;

import java.io.IOException;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

public class JSON {

    //public final JsonObject jsonObject;
    public final JsonValue jsonValue;
    public final PushbackLexParser pushbackLexParser;



    public JSON(PushbackLexParser pushbackLexParser) throws IOException {
        //this.jsonObject = jsonObject(pushbackLexParser);
        this.pushbackLexParser = pushbackLexParser;
        this.jsonValue = parse();
        /*if ( jsonValue instanceof JsonObject)
        {
            this.jsonObject = (JsonObject) this.jsonValue;
        }
        else
        {
            jsonObject = null;
        }*/
    }

    private JsonObject jsonObject(PushbackLexParser lexParser) throws IOException
    {
        JSONSymbol symbol = lexParser.nextSkipSpaces();

        if ( symbol.type != OPEN_BRACE)
        {
            throw new JsonParseException("JSON Object must start with {");
        }

        JsonObject jsonObject = new JsonObject(lexParser);

        return jsonObject;

    }

    public JsonValue parse() throws IOException
    {
        //JSONSymbol symbol = lexParser.nextSkipSpaces();


        JsonValue jsonValue = JsonValueBuilder.buildJsonValue(pushbackLexParser);

        return jsonValue;
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
