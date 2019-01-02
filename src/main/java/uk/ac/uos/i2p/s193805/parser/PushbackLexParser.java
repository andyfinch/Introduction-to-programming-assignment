package uk.ac.uos.i2p.s193805.parser;

import java.io.IOException;
import java.util.Stack;

/**
 * Wraps a LexParser to allow for JSON Symbols to pushed back and read again.
 */
public class PushbackLexParser {

    private LexParser lexParser;
    private Stack<JSONSymbol> symbols;
    private JSONSymbol symbol;

    /**
     * Instantiates a new Pushback lex parser.
     *
     * @param lexParser the lex parser
     */
    public PushbackLexParser(LexParser lexParser) {
        this.lexParser = lexParser;
        this.symbols = new Stack<>();
    }

    /**
     * Next json symbol.
     *
     * @return the json symbol
     * @throws IOException the io exception
     */
    public JSONSymbol next() throws IOException {
        if (!symbols.isEmpty()) return symbols.pop();
        return lexParser.next();
    }

    /**
     * Next skip spaces json symbol.
     *
     * @return the json symbol
     * @throws IOException the io exception
     */
    public JSONSymbol nextSkipSpaces() throws IOException {
        //JSONSymbol symbol;
        if (!symbols.isEmpty())
        {
            symbol = symbols.pop();
            if (symbol.type == JSONSymbol.Type.SPACE)
            {
                return next();
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

    /**
     * Unread.
     *
     * @param symbol the symbol
     */
    public void unread(JSONSymbol symbol) {
        symbols.push(symbol);
    }

    /**
     * Gets symbols.
     *
     * @return the symbols
     */
    public Stack<JSONSymbol> getSymbols() {
        return symbols;
    }

    /**
     * Gets current symbol.
     *
     * @return the current symbol
     */
    public JSONSymbol getCurrentSymbol() {
        return symbol;
    }
}
