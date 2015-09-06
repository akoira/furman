package by.dak.furman.templateimport.values;

public class Message extends AValue
{
    private String message;
    private Exception exception;
    private Object[] params;

    public static Message valueOf(String message, Object... param)
    {
        return valueOf(message, null, param);
    }

    public static Message valueOf(String message, Exception exception, Object... param)
    {
        assert message != null;

        Message result = new Message();
        result.setMessage(message);
        result.setException(exception);
        result.params = param;
        return result;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return message;
    }

    public Exception getException()
    {
        return exception;
    }

    public void setException(Exception exception)
    {
        this.exception = exception;
    }

    public Object[] getParams()
    {
        return params;
    }

    public void setParams(Object[] params)
    {
        this.params = params;
    }
}
