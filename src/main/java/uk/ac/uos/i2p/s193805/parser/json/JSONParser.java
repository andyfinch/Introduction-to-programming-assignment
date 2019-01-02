package uk.ac.uos.i2p.s193805.parser.json;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.LexParser;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonObject;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonParsable;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValue;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonValueBuilder;

import java.io.IOException;
import java.io.Reader;


/**
 * Entry point for parsing any JSON String
 */
public class JSONParser implements JsonParsable {

    private PushbackLexParser pushBackLexParser;

    /**
     * Instantiates a new Json parser.
     *
     * @param reader the reader
     */
    public JSONParser(Reader reader) {

        LexParser lexParser = new LexParser(reader);
        pushBackLexParser = new PushbackLexParser(lexParser);

    }


    /**
     * Does some simple validation of passes of to JsonValueBuilder
     * @return a JsonObject.  Cannot be used if JSON uses Value types only
     * @throws IOException
     */
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

    /**
     * Allows any valid JSON which is not a JSON object to be parsed
     *
     * @return the json value
     * @throws IOException the io exception
     */
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


