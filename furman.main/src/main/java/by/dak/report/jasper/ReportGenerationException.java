package by.dak.report.jasper;

/**
 * @author Denis Koyro
 * @version 0.1 26.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
@Deprecated
public class ReportGenerationException extends Exception
{
    public ReportGenerationException()
    {
    }

    public ReportGenerationException(String message)
    {
        super(message);
    }

    public ReportGenerationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ReportGenerationException(Throwable cause)
    {
        super(cause);
    }
}
