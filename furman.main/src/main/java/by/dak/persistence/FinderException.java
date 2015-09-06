package by.dak.persistence;

/**
 * @author dkoyro
 * @version 0.1 20.10.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class FinderException extends Exception
{
    public FinderException(Throwable cause)
    {
        super(cause);
    }

    public FinderException(String message)
    {
        super(message);
    }

    public FinderException(String message, Throwable cause)
    {
        super(message, cause);
    }
}