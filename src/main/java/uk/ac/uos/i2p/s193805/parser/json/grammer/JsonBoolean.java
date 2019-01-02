package uk.ac.uos.i2p.s193805.parser.json.grammer;

/**
 * JsonBoolean object holding the boolean value
 */
public class JsonBoolean implements JsonValue {

    /**
     * The Boolean value.
     */
    public final Boolean booleanValue;

    /**
     * Instantiates a new Json boolean.
     *
     * @param booleanValue the boolean value
     */
    public JsonBoolean(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    @Override
    public String toString() {
        return this.booleanValue.toString();
    }

    /**
     * To check what type of JsonValue this is.
     *
     * @return the type of JsonValue
     */
    @Override
    public ValueType getJsonValueType() {
        return ValueType.JSON_BOOLEAN;
    }
}
