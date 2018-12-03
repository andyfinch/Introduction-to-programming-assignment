package uk.ac.uos.i2p.s193805.parser;

import java.io.IOException;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 29/11/2018
 * Time: 09:23
 */

public class PushbackLexParser {

    private LexParser lexParser;
    private Stack<JSONSymbol> symbols;

    public PushbackLexParser(LexParser lexParser) {
        this.lexParser = lexParser;
        this.symbols = new Stack<>();
    }

    public JSONSymbol next() throws IOException {
        if (!symbols.isEmpty()) return symbols.pop();
        return lexParser.next();
    }

    public JSONSymbol nextSkipSpaces() throws IOException {
        JSONSymbol symbol;
        if (!symbols.isEmpty())
        {
            symbol = symbols.pop();
            if (symbol.type == JSONSymbol.Type.SPACE)
            {
                return symbols.pop();
            }

            return symbol;
        }
        else
        {
            symbol = lexParser.next();
            if (symbol.type == JSONSymbol.Type.SPACE)
            {
                symbol = lexParser.next();
            }
        }
        return symbol;
    }

    public void unread(JSONSymbol symbol) {
        symbols.push(symbol);
    }

    public Stack<JSONSymbol> getSymbols() {
        return symbols;
    }
}
