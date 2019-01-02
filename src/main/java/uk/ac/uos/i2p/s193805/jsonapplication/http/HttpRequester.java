package uk.ac.uos.i2p.s193805.jsonapplication.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * Contains methods to send HTTP GET and POST requests
 */
public class HttpRequester {


    /**
     * Helper method to get an HttpURLConnection
     * @param urlAddress
     * @param requestMethod
     * @return an instance of an HttpURLConnection
     * @throws IOException
     */
    private HttpURLConnection setupConnection(String urlAddress, String requestMethod) throws IOException
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

    /**
     * Send GET Http request
     *
     * @param urlAddress the url address
     * @return HttpResponseVO with body and response code
     * @throws IOException the io exception
     */
    public HttpResponseVO sendGET(String urlAddress) throws IOException
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
        httpResponseVO.setRequestURL(urlAddress);

        return httpResponseVO;
    }

    /**
     * Send POST http request
     *
     * @param urlAddress the url address
     * @param body       the body
     * @return HttpResponseVO with body and response code
     * @throws IOException the io exception
     */
    public HttpResponseVO sendPOST(String urlAddress, String body) throws IOException
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
        httpResponseVO.setRequestURL(urlAddress);

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
