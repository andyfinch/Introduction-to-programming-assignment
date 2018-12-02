package uk.ac.uos.i2p.s193805.parser.json.grammer;

public class JsonMember {


    public final String key;
    public final JsonValue jsonValue;

    public JsonMember(String key, JsonValue jsonValue) {
        this.key = key;
        this.jsonValue = jsonValue;
    }
}
