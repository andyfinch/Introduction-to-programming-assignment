package uk.ac.uos.i2p.s193805.parser.json;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValueBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 30/10/2018
 * Time: 08:21
 */

public class JSONParser {

    JsonValue jsonValue;
    private PushbackLexParser pushBackLexParser;

    public JSONParser(Reader reader) {

        LexParser lexParser = new LexParser(reader);
        pushBackLexParser = new PushbackLexParser(lexParser);

    }

    public JsonObject parse () throws IOException
    {
        JSONSymbol symbol = pushBackLexParser.nextSkipSpaces();

        if ( symbol.type != JSONSymbol.Type.OPEN_BRACE)
        {
            throw new JsonParseException("JSON Object must start with {");
        }

        pushBackLexParser.unread(symbol);

        JsonValue jsonValue = new JsonValueBuilder(pushBackLexParser).parse();

        if ( jsonValue.getJsonValueType() == JsonValue.ValueType.JSON_OBJECT)
        {
            return (JsonObject) jsonValue;
        }
        else
        {
            throw new JsonParseException("Parse returns a JsonObject. This is a " + jsonValue.getJsonValueType() + ". Use parseAsValue");
        }

    }

    public JsonValue parseAsValue() throws IOException {

        JsonValue jsonValue = new JsonValueBuilder(pushBackLexParser).parse();

        JSONSymbol symbol = pushBackLexParser.nextSkipSpaces();

        if ( symbol.type != JSONSymbol.Type.END)
        {
           throw new JsonParseException("Expected EOF but got " + symbol.value);
        }

        return jsonValue;
    }


}


