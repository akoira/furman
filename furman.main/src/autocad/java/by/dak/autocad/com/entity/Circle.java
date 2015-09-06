package by.dak.autocad.com.entity;

import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 15:20
 */
public class Circle extends AEntity
{
    public Circle(Dispatch dispatch)
    {
        super(dispatch);
        setCurve(true);
    }
}
