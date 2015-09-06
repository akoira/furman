package by.dak.autocad.com.entity;

import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 15:46
 */
public class Ellipse extends AEntity
{
    public Ellipse(Dispatch dispatch)
    {
        super(dispatch);
        setCurve(true);
    }
}
