package uk.ac.uos.i2p.s193805.parser.json.grammer;

/**
 * Allows all Json type objects to be a JsonValue
 */
public interface JsonValue {

    /**
     * The enum Value type.
     */
    enum ValueType
    {
        /**
         * Json object value type.
         */
        JSON_OBJECT,
        /**
         * Json array value type.
         */
        JSON_ARRAY,
        /**
         * Json string value type.
         */
        JSON_STRING,
        /**
         * Json number value type.
         */
        JSON_NUMBER,
        /**
         * Json boolean value type.
         */
        JSON_BOOLEAN
    }

    /**
     * Gets json value type.
     *
     * @return the json value type
     */
    ValueType getJsonValueType();

}
