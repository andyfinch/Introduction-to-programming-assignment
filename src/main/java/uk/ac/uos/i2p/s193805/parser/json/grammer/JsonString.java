package uk.ac.uos.i2p.s193805.parser.json.grammer;

/**
 * JsonString object which holds a String value
 */
public class JsonString implements JsonValue {

    /**
     * The String value.
     */
    public final String stringValue;

    /**
     * Instantiates a new Json string.
     *
     * @param stringValue the string value
     */
    public JsonString(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    @Override
    public ValueType getJsonValueType() {
        return ValueType.JSON_STRING;
    }
}
