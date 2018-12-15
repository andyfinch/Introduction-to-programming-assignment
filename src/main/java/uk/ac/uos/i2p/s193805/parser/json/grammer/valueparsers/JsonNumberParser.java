package uk.ac.uos.i2p.s193805.parser.json.grammer.valueparsers;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;
import uk.ac.uos.i2p.s193805.parser.json.grammer.JsonNumber;

import java.io.IOException;
import java.math.BigDecimal;

public class JsonNumberParser {

    public final PushbackLexParser pushbackLexParser;

    public JsonNumberParser(PushbackLexParser pushbackLexParser)  {

        this.pushbackLexParser = pushbackLexParser;

    }

    public JsonNumber parse() throws IOException {
        StringBuilder numberString = new StringBuilder();
        JSONSymbol symbol = pushbackLexParser.nextSkipSpaces();

        if (symbol.type != JSONSymbol.Type.MINUS_SIGN && symbol.type != JSONSymbol.Type.NUMBER) {
            throw new JsonParseException("Number must start with - or Integer");
        }

        if ( symbol.type == JSONSymbol.Type.MINUS_SIGN)
        {

            numberString.append(symbol.value); // -

            symbol = pushbackLexParser.next();

            if (symbol.type != JSONSymbol.Type.NUMBER)
            {
                throw new JsonParseException("Number must follow minus sign");
            }

        }

        numberString.append(symbol.value); // -1 or 1

        symbol = pushbackLexParser.next();


        if (symbol.type == JSONSymbol.Type.DECIMAL_POINT || "E".equalsIgnoreCase(symbol.value))
        {
            if (symbol.type == JSONSymbol.Type.DECIMAL_POINT)
            {
                numberString.append(symbol.value); //-1. or 1.

                symbol = pushbackLexParser.next();

                if (symbol.type != JSONSymbol.Type.NUMBER)
                {
                    throw new JsonParseException("Number must follow decimal point");
                }

                numberString.append(symbol.value); //-1.1 or 1.1

                symbol = pushbackLexParser.next();

            }

            if ("E".equalsIgnoreCase(symbol.value))
            {
                numberString.append(symbol.value);

                symbol = pushbackLexParser.next();

                if (symbol.type != JSONSymbol.Type.NUMBER && symbol.type != JSONSymbol.Type.MINUS_SIGN && symbol.type != JSONSymbol.Type.PLUS_SIGN)
                {
                    throw new JsonParseException("Exponent E must be followed by number or sign");
                }

                numberString.append(symbol.value);

                symbol = pushbackLexParser.next();

                if ( symbol.type == JSONSymbol.Type.NUMBER)
                {
                    numberString.append(symbol.value);
                }

                symbol = pushbackLexParser.nextSkipSpaces();

                testNumberEnd(symbol, numberString);

                pushbackLexParser.unread(symbol);




            }
            else
            {
                testNumberEnd(symbol, numberString);
            }
        }
        else
        {
            testNumberEnd(symbol, numberString);
            pushbackLexParser.unread(symbol);

        }

        return new JsonNumber( new BigDecimal(numberString.toString()));


    }

    private void testNumberEnd(JSONSymbol jsonSymbol, StringBuilder numberString)
    {
        if ( jsonSymbol.type != JSONSymbol.Type.COMMA
                && jsonSymbol.type != JSONSymbol.Type.CLOSE_ARRAY
                && jsonSymbol.type != JSONSymbol.Type.CLOSE_BRACE
                && jsonSymbol.type != JSONSymbol.Type.END
                && jsonSymbol.type != JSONSymbol.Type.SPACE)
        {
            numberString.append(jsonSymbol.value);
            throw new JsonParseException("Invalid JSON number " + numberString);
        }
    }

}

