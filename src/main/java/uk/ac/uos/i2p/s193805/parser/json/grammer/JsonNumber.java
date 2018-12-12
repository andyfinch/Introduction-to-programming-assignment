package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;
import uk.ac.uos.i2p.s193805.parser.exceptions.JsonParseException;

import java.io.IOException;
import java.math.BigDecimal;

public class JsonNumber implements JsonValue {

    public final BigDecimal number;
    public JsonNumber(BigDecimal number) {
        this.number = number;
    }



    /*public JsonValue parse() throws IOException {
        StringBuilder numberString = new StringBuilder();
        JSONSymbol symbol = pushbackLexParser.next();

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

            //pushbackLexParser.unread(symbol);


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


        return new JSONN( new BigDecimal(numberString.toString()));


    }*/

    public int intValue()
    {
        return number.intValue();
    }

    @Override
    public String toString() {
        return this.number.toString();
    }
}
