package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValueBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 13/12/2018
 * Time: 09:43
 */

public class JsonObjectParser {

    private PushbackLexParser pushbackLexParser;
    private Map<String, JsonValue> jsonValueMap = new HashMap<>();


    public JsonObjectParser(PushbackLexParser pushbackLexParser) throws IOException {

        this.pushbackLexParser = pushbackLexParser;




    }

    public JsonObject parse() throws IOException
    {
        JSONSymbol symbol = pushbackLexParser.getCurrentSymbol();

        while (symbol.type != JSONSymbol.Type.CLOSE_BRACE)
        {
            if (symbol.type == END)
            {
                throw new JsonParseException("JSON Object must end with }");
            }

            buildKeyValue();
            symbol = pushbackLexParser.nextSkipSpaces();
        }

        return new JsonObject(jsonValueMap);

    }

    private void buildKeyValue() throws IOException {



        String key = key();
        if (null == key)
        {
            throw new JsonParseException("Json member must have string key");
        }
        JsonValue value = value();

        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();

        if ((symbol.type != CLOSE_BRACE && symbol.type != END) && symbol.type != COMMA)
        {
            throw new JsonParseException("Members must be seperated by commas, found symbol " + symbol.type.name() + " symbol value " + symbol.value);
        }
        if (symbol.type == CLOSE_BRACE || symbol.type == COMMA)
        {
            pushbackLexParser.unread(symbol);
        }

        /*if (symbol.type == COMMA)
        {
            pushbackLexParser.unread(symbol);
        }*/

        jsonValueMap.put(key, value);
    }

    private String key() throws IOException {
        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();
        StringBuilder key = new StringBuilder();

        if (END == symbol.type) return null;

        if (symbol.type != QUOTE) return null;

        while ((symbol = pushbackLexParser.nextSkipSpaces()).type != QUOTE)
        {
            if (symbol.type == END)
            {
                throw new JsonParseException("Quoted String must end with \"");
            }
            key.append(symbol.value);
        }

        symbol = pushbackLexParser.nextSkipSpaces();

        if (symbol.type != COLON)
        {
            throw new JsonParseException("Key must be followed by :");
        }

        return key.toString();
    }

    private JsonValue value() throws IOException {

        JsonValue jsonValue = new JsonValueBuilder(pushbackLexParser).parse();

        if (jsonValue == null) return null;

        return jsonValue;

    }
}
