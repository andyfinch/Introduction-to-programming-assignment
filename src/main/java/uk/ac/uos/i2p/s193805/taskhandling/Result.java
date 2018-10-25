package uk.ac.uos.i2p.s193805.taskhandling;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:40
 */

public class Result {

    private String answer;
    private String response;

    public boolean isCorrect()
    {
        if ( "200 OK".equalsIgnoreCase(response))
        {
            return true;
        }

        return false;
    }

    public boolean isNumeric()
    {
        if ( response != null)
        {
            try
            {
                Double.parseDouble(response);
            } catch (NumberFormatException e)
            {
                return false;
            }
            return true;
        }

        return false;

    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
