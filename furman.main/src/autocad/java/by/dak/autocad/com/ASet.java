package by.dak.autocad.com;

import by.dak.utils.GenericUtils;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 13:25
 */
public abstract class ASet<E> extends AObject
{

    public ASet(Dispatch dispatch)
    {
        super(dispatch);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected E create(Dispatch dispatch)
    {
        try
        {
            Class<E> aClass = GenericUtils.getParameterClass(this.getClass(), 0);
            return aClass.getConstructor(Dispatch.class).newInstance(dispatch);
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public E Item(int index)
    {
        return create(this.invoke("Item", index).getDispatch());
    }

    public E Item(String name)
    {
        return create(this.invoke("Item", name).getDispatch());
    }

    public int getCount()
    {
        return this.getPropertyAsInt("Count");
    }

    public E Add(String name, boolean modelType)
    {
        return create(this.invoke("Add", new Variant(name), new Variant(modelType)).getDispatch());
    }

}
