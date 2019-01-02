package uk.ac.uos.i2p.s193805.parser;


/**
 * The type Json symbol.
 */
public class JSONSymbol {

    /**
     * The enum Type.
     */
    public enum Type {
        /**
         * Open brace type.
         */
        OPEN_BRACE,
        /**
         * Close brace type.
         */
        CLOSE_BRACE,
        /**
         * Quote type.
         */
        QUOTE,
        /**
         * Colon type.
         */
        COLON,
        /**
         * Comma type.
         */
        COMMA,
        /**
         * Open array type.
         */
        OPEN_ARRAY,
        /**
         * Close array type.
         */
        CLOSE_ARRAY,
        /**
         * String type.
         */
        STRING,
        /**
         * Number type.
         */
        NUMBER,
        /**
         * Minus sign type.
         */
        MINUS_SIGN,
        /**
         * Plus sign type.
         */
        PLUS_SIGN,
        /**
         * Decimal point type.
         */
        DECIMAL_POINT,
        /**
         * Space type.
         */
        SPACE,
        /**
         * End type.
         */
        END,
        /**
         * Other type.
         */
        OTHER
    }

    /**
     * The Type.
     */
    public final Type type;
    /**
     * The Value.
     */
    public final String value;

    /**
     * Instantiates a new Json symbol.
     *
     * @param type  the type
     * @param value the value
     */
    public JSONSymbol(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Instantiates a new Json symbol.
     *
     * @param type the type
     */
    public JSONSymbol(Type type) {
        this(type, null);
    }
}
