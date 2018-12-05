package uk.ac.uos.i2p.s193805.http;

import org.junit.jupiter.api.Test;

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
        HttpRequester httpRequest = new HttpRequester();
        try
        {
            HttpRequester.sendGET(url);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("test");
    }


}