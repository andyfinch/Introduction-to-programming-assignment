package uk.ac.uos.i2p.s193805.parser.json.grammer;

import java.math.BigDecimal;

/**
 * JsonNumber object holding BigDecimal value of the number.
 */
public class JsonNumber implements JsonValue {

    /**
     * The Number.
     */
    public final BigDecimal number;

    /**
     * Instantiates a new Json number.
     *
     * @param number the number
     */
    public JsonNumber(BigDecimal number) {
        this.number = number;
    }


    /**
     * Helper method to get BigDecimal int value
     *
     * @return the int
     */
    public int intValue()
    {
        return number.intValue();
    }

    @Override
    public String toString() {
        return this.number.toString();
    }

    /**
     * To check what type of JsonValue this is.
     *
     * @return the type of JsonValue
     */
    @Override
    public ValueType getJsonValueType() {
        return ValueType.JSON_NUMBER;
    }
}
