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
        JSONSymbol symbol = pushbackLexParser.getCurrentSymbol();

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


        if (symbol.type == JSONSymbol.Type.DECIMAL_POINT || symbol.value.equalsIgnoreCase("E"))
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

            if ( symbol.value.equalsIgnoreCase("E"))
            {
                numberString.append(symbol.value);

                symbol = pushbackLexParser.next();

                if (symbol.type != JSONSymbol.Type.MINUS_SIGN && symbol.type != JSONSymbol.Type.PLUS_SIGN)
                {
                    throw new JsonParseException("Exponent E must be followed by sign");
                }

                numberString.append(symbol.value);

                symbol = pushbackLexParser.next();

                if ( symbol.type != JSONSymbol.Type.NUMBER)
                {
                    throw new JsonParseException("Exponent sign must be followed by number");
                }

                numberString.append(symbol.value);
            }
        }
        else
        {
            pushbackLexParser.unread(symbol);
        }

        return new JsonNumber( new BigDecimal(numberString.toString()));


    }


}
