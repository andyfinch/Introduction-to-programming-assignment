package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling;

/**
 * Created by IntelliJ IDEA.
 * User: finchaj
 * Date: 25/10/2018
 * Time: 09:40
 */

public class Result {

    private String answer;
    private int response;

    public boolean isCorrect()
    {
        return 200 == response;

    }

    public boolean isNumeric()
    {
        if (answer != null)
        {
            try
            {
                Integer.parseInt(answer);
            } catch (NumberFormatException e)
            {
                return false;
            }
            return true;
        }

        return false;

    }

    public int getAnswerIntValue()
    {
        if ( !isNumeric() )
        {
            throw new NumberFormatException("Answer is not numeric " + answer);

        }
        else
        {
            return Integer.parseInt(answer);
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
