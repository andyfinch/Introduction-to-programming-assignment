package uk.ac.uos.i2p.s193805.parser.json.grammer;

import uk.ac.uos.i2p.s193805.parser.LexParser;

import java.io.IOException;

public class JsonObject {

    //has members
    public final JsonMember jsonMember;

    public JsonObject(JsonMember jsonMember) {
        this.jsonMember = jsonMember;
    }

    private JsonObject jsonObject(LexParser lexParser) throws IOException
    {
        JsonMember jsonMember = jsonMember(lexParser);
        if (null == jsonMember)
        {
            throw new RuntimeException("No members");
        }

        return new JsonObject(jsonMember);
    }

    private JsonMember jsonMember(LexParser lexParser) throws IOException
    {
        //must start with Jsonstring:
        //then contain :
        //then contain Json Value - Object, array etc
        
    }
}
