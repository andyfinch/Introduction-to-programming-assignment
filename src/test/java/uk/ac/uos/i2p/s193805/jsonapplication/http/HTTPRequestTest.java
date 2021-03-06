package uk.ac.uos.i2p.s193805.jsonapplication.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by IntelliJ IDEA.
 * User: S193805
 * Date: 02/11/2018
 * Time: 14:13
 */

class HTTPRequestTest {

    public final static String url = "http://i2j.openode.io/student?id=s193805";

    @Test
    void testHTTP() {

        try
        {
            HttpResponseVO httpResponseVO = new HttpRequester().sendGET(url);
            assertEquals(200, httpResponseVO.getResponse());
        } catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }

    }


}