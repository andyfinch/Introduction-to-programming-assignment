package uk.ac.uos.i2p.s193805.parser.json.grammer;

import java.math.BigDecimal;

public class JsonNumber implements JsonValue {

    public final BigDecimal number;
    public JsonNumber(BigDecimal number) {
        this.number = number;
    }


    public int intValue()
    {
        return number.intValue();
    }

    @Override
    public String toString() {
        return this.number.toString();
    }

    @Override
    public ValueType getJsonValueType() {
        return ValueType.JSON_NUMBER;
    }
}
