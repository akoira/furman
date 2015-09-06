package by.dak.autocad.com.entity;

import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 15:38
 */
public class Arc extends AEntity implements ILength
{
    public Arc(Dispatch dispatch)
    {
        super(dispatch);
        setCurve(true);
    }

    public double getArcLength()
    {
        return this.getProperty("ArcLength").getDouble();
    }


    @Override
    public double getLength()
    {
        return getArcLength();
    }
}
