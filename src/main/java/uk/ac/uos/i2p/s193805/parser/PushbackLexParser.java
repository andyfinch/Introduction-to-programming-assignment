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

    public void unread(JSONSymbol symbol) {
        symbols.push(symbol);
    }


}
