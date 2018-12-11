package uk.ac.uos.i2p.s193805.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 02/11/2018
 * Time: 14:06
 */

public class HttpRequester {

   /* private int response;
    private String body;*/

    private static HttpURLConnection setupConnection(String urlAddress, String requestMethod) throws Exception
    {
        URL url = new URL(urlAddress);
        HttpURLConnection con;

        boolean useProxy = System.getProperty("proxyHost") != null;

        if ( useProxy)
        {
            String proxyHost = System.getProperty("proxyHost");
            String proxyPort = System.getProperty("proxyPort");
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort)));

            con = (HttpURLConnection) url.openConnection(proxy);
        }
        else
        {
            con = (HttpURLConnection) url.openConnection();
        }

        con.setRequestMethod(requestMethod);

        return con;
    }

    public static HttpResponseVO sendGET(String urlAddress) throws Exception
    {
        HttpResponseVO httpResponseVO = new HttpResponseVO();

        HttpURLConnection con = setupConnection(urlAddress, "GET");
        con.connect();
        httpResponseVO.setResponse(con.getResponseCode());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
        {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        httpResponseVO.setBody(content.toString());

        return httpResponseVO;
    }

    public static HttpResponseVO sendPOST(String urlAddress, String body) throws Exception
    {


        HttpResponseVO httpResponseVO = new HttpResponseVO();


        HttpURLConnection con = setupConnection(urlAddress, "POST");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        osw.write(body);
        osw.flush();
        osw.close();
        os.close();
        con.connect();

        httpResponseVO.setResponse(con.getResponseCode());

        if ( httpResponseVO.getResponse() == 200 )
        {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
            {
                content.append(inputLine);
            }
            in.close();

            httpResponseVO.setBody(content.toString());
        }

        con.disconnect();
        
        return httpResponseVO;



    }

}
