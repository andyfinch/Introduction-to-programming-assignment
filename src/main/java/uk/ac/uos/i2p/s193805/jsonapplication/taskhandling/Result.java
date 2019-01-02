package uk.ac.uos.i2p.s193805.jsonapplication.taskhandling;

/**
 * Result class to result of Add, Concatenating or Multiplying
 */
public class Result {

    private String answer;
    private int response;

    /**
     * Is correct boolean.
     *
     * @return the boolean
     */
    public boolean isCorrect()
    {
        return 200 == response;

    }

    /**
     * Is numeric boolean.
     *
     * @return the boolean
     */
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

    /**
     * Gets answer int value.
     *
     * @return the answer int value
     */
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

    /**
     * Gets answer.
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets answer.
     *
     * @param answer the answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

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
}
