package by.dak.autocad.com;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 27.03.11
 * Time: 20:34
 */
public abstract class AObject extends ActiveXComponent
{
    public AObject(Dispatch dispatch)
    {
        super(dispatch);
    }

    public String getObjectName()
    {
        return this.getPropertyAsString("ObjectName");
    }

    @Override
    public String toString()
    {
        return getObjectName();
    }
}
