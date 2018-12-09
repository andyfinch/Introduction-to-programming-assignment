package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.JSONSymbol;
import uk.ac.uos.i2p.s193805.parser.PushbackLexParser;

import java.io.IOException;
import java.math.BigDecimal;

public class JsonNumber {

    public final BigDecimal number;

    public JsonNumber(PushbackLexParser pushbackLexParser) throws IOException {

        number = number(pushbackLexParser);

    }

    public BigDecimal number(PushbackLexParser pushbackLexParser) throws IOException {
        StringBuilder numberString = new StringBuilder();
        JSONSymbol symbol = pushbackLexParser.next();

        if (symbol.type != JSONSymbol.Type.MINUS_SIGN && symbol.type != JSONSymbol.Type.NUMBER) {
            throw new RuntimeException("Number must start with - or Integer");
        }



        if ( symbol.type == JSONSymbol.Type.MINUS_SIGN)
        {

            numberString.append(symbol.value); // -

            symbol = pushbackLexParser.next();

            if (symbol.type != JSONSymbol.Type.NUMBER)
            {
                throw new RuntimeException("Number must follow minus sign");
            }

            //pushbackLexParser.unread(symbol);


        }

        numberString.append(symbol.value); // -1 or 1

        symbol = pushbackLexParser.next();


        if (symbol.type == JSONSymbol.Type.DECIMAL_POINT)
        {
            numberString.append(symbol.value); //-1. or 1.

            symbol = pushbackLexParser.next();

            if (symbol.type != JSONSymbol.Type.NUMBER)
            {
                throw new RuntimeException("Number must follow decimal point");
            }

            numberString.append(symbol.value); //-1.1 or 1.1


        }

        if ( symbol.value.equals("E"))
        {
            numberString.append(symbol.value);

            symbol = pushbackLexParser.next();

            if (symbol.type != JSONSymbol.Type.MINUS_SIGN && symbol.type != JSONSymbol.Type.PLUS_SIGN)
            {
                throw new RuntimeException("Exponent E must be followed by sign");
            }

            numberString.append(symbol.value);

            symbol = pushbackLexParser.next();

            if ( symbol.type != JSONSymbol.Type.NUMBER)
            {
                throw new RuntimeException("Exponent sign must be followed by number");
            }

            numberString.append(symbol.value);
        }

        pushbackLexParser.unread(symbol);

        return new BigDecimal(numberString.toString());


    }

    public int intValue()
    {
        return number.intValue();
    }

}
