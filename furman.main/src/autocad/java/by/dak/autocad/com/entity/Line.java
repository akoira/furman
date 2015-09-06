package by.dak.autocad.com.entity;

import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 15:22
 */
public class Line extends AEntity implements ILength
{
    public Line(Dispatch dispatch)
    {
        super(dispatch);
    }

    @Override
    public double getLength()
    {
        return this.getProperty("Length").getDouble();
    }
}
