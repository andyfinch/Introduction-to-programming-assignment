package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.COLON;
import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.SPACE;

public class JsonObject {

    //has members
    //public final JsonMember jsonMember;
    public final Map<String, JsonValue> jsonValueMap = new HashMap<>();

    /*public JsonObject(JsonMember jsonMember) {
        this.jsonMember = jsonMember;
    }*/

    public JsonObject(PushbackLexParser pushbackLexParser) throws IOException {

        JsonMember jsonMember;
        while ( (jsonMember = jsonMember(pushbackLexParser)) != null)
        {
            //JsonMember jsonMember = jsonMember(pushbackLexParser);
            jsonValueMap.put(jsonMember.key, jsonMember.jsonValue);
        }

        //this.jsonMember = jsonMember(pushbackLexParser);
    }

    /*private JsonObject jsonObject(PushbackLexParser lexParser) throws IOException
    {
        JsonMember jsonMember = jsonMember(lexParser);
        if (null == jsonMember)
        {
            throw new RuntimeException("No members");
        }

        return new JsonObject(jsonMember);
    }*/

    private JsonMember jsonMember(PushbackLexParser lexParser) throws IOException {

        JSONSymbol symbol = lexParser.nextSkipSpaces();

        if ( symbol.type == JSONSymbol.Type.END || symbol.type == JSONSymbol.Type.CLOSE_BRACE)
        {
            return null;
        }
        lexParser.unread(symbol);

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
    }

    private String key(PushbackLexParser lex) throws IOException {
        JSONSymbol symbol = lex.nextSkipSpaces();
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
    }

    private JsonValue value(PushbackLexParser lex) throws IOException {

        JsonValue jsonValue = new JsonValue(lex);

        if ( jsonValue.jsonValue == null) return null;

        return jsonValue;

    }



}
