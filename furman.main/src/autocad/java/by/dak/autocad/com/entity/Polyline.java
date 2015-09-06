package by.dak.autocad.com.entity;

import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 15:20
 */
public class Polyline extends AEntity implements ILength
{
    public Polyline(Dispatch dispatch)
    {
        super(dispatch);
        setCurve(true);
    }

    public double getLength()
    {
        return this.getProperty("Length").getDouble();
    }
}
