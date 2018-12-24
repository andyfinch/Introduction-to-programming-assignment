package uk.ac.uos.i2p.s193805.jsonapplication.http;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 02/11/2018
 * Time: 14:13
 */

class HTTPRequestTest {

    public final static String url = "http://i2j.openode.io/student?id=s193805";

    @Test
    void testHTTP() {

        try
        {
            HttpResponseVO httpResponseVO = HttpRequester.sendGET(url);
            assertEquals(200, httpResponseVO.getResponse());
        } catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }

    }


}