package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;
import java.math.BigDecimal;

import static uk.ac.uos.i2p.s193805.parser.JSONSymbol.Type.*;

public interface JsonValue {

    /*public final Object object;

    public JsonValue(PushbackLexParser pushbackLexParser) throws IOException {

        this.object = value(pushbackLexParser);
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
        else if ( symbol.type == NUMBER || symbol.type == MINUS_SIGN)
        {
            lex.unread(symbol);
            return new JsonNumber(lex);
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
        else if (symbol.type == OPEN_BRACE)
        {
            return new JsonObject(lex);
        }
        else if (symbol.type == OPEN_ARRAY)
        {
            return new JsonArray(lex);
        }

        return null;
    }

    public JsonObject getJsonObject() {

        if (!(object instanceof JsonObject))
        {
            throw new RuntimeException("Requested Json Object is not a JSON Object. It is a " + this.getClass());
        }

        return (JsonObject) object;

    }

    public String getJSONString() {

        if (!(object instanceof String))
        {
            throw new RuntimeException("Requested String is not a JSON String. It is a" + this.getClass());
        }

        return (String) object;
    }

    public JsonNumber getJSONNumber() {

        if (!(object instanceof JsonNumber))
        {
            throw new RuntimeException("Requested number is not a JSON Number. It is a" + this.getClass());
        }

        return (JsonNumber) object;


    }

    public Boolean getJsonBoolean() {

        String booleanString = String.valueOf(object);
        if (booleanString.equals("true") || booleanString.equals("false"))
        {
            return Boolean.valueOf(booleanString);
        }
        else
        {
            throw new RuntimeException("Cannot convert value to Boolean " + object);

        }


    }

    public JsonArray getJsonArray() {

        if (!(object instanceof JsonArray))
        {
            throw new RuntimeException("Requested Json Array is not a JSON Array. It is a " + this.getClass());
        }

        return (JsonArray) object;

    }*/


}
