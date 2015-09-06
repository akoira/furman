package by.dak.profile;

/**
 * User: akoyro
 * Date: 26.08.11
 * Time: 8:18
 */
public class PExpressions
{
    public static final String PROPERTY_widthExpression = "widthExpression";
    public static final String PROPERTY_lengthExpression = "lengthExpression";
    public static final String PROPERTY_countExpression = "countExpression";

    private String widthExpression;
    private String lengthExpression;
    private String countExpression;

    public String getWidthExpression()
    {
        return widthExpression;
    }

    public void setWidthExpression(String widthExpression)
    {
        this.widthExpression = widthExpression;
    }

    public String getLengthExpression()
    {
        return lengthExpression;
    }

    public void setLengthExpression(String lengthExpression)
    {
        this.lengthExpression = lengthExpression;
    }

    public String getCountExpression()
    {
        return countExpression;
    }

    public void setCountExpression(String countExpression)
    {
        this.countExpression = countExpression;
    }
}
