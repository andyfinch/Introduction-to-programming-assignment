package uk.ac.uos.i2p.s193805.parser;


public class JSONSymbol {

    public enum Type {
        OPEN_BRACE,
        CLOSE_BRACE,
        QUOTE,
        COLON,
        COMMA,
        OPEN_ARRAY,
        CLOSE_ARRAY,
        STRING,
        NUMBER,
        MINUS_SIGN,
        PLUS_SIGN,
        DECIMAL_POINT,
        SPACE,
        END,
        OTHER
    }

    public final Type type;
    public final String value;

    public JSONSymbol(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public JSONSymbol(Type type) {
        this(type, null);
    }
}
