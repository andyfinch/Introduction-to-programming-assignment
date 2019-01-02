package uk.ac.uos.i2p.s193805.jsonapplication.http;

/**
 * The type Http response vo.
 */
public class HttpResponseVO {

    private int response;
    private String body;
    private String requestURL;

    /**
     * Gets response.
     *
     * @return the response
     */
    public int getResponse() {
        return response;
    }

    /**
     * Sets response.
     *
     * @param response the response
     */
    public void setResponse(int response) {
        this.response = response;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets request url.
     *
     * @return the request url
     */
    public String getRequestURL() {
        return requestURL;
    }

    /**
     * Sets request url.
     *
     * @param requestURL the request url
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
}
