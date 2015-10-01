package by.dak.autocad.com.entity;

import by.dak.autocad.com.AObject;
import by.dak.autocad.com.Application;
import by.dak.autocad.com.Linetype;
import com.jacob.com.Dispatch;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: akoyro
 * Date: 27.03.11
 * Time: 21:25
 */
public abstract class AEntity extends AObject implements IEntity
{

    public static final int COLOR_BLACK = 256;
    private boolean curve = false;

    public AEntity(Dispatch dispatch)
    {
        super(dispatch);
    }

    public boolean isCurve()
    {
        return curve;
    }

    protected void setCurve(boolean curve)
    {
        this.curve = curve;
    }


    public Application getApplication()
    {
        return new Application(getProperty("Application").getDispatch());
    }


    public Linetype getLinetype()
    {
        String name = this.getPropertyAsString("Linetype");
        if (name != null)
        {
            try
            {
                return Linetype.valueOf(name);
            }
            catch (IllegalArgumentException e)
            {
                Logger.getLogger(AEntity.class.getName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
        return Linetype.ByLayer;
    }

    public int getColor() {
        return this.getProperty("Color").getInt();
    }



    public String getHandle()
    {
        return getProperty("Handle").getString();
    }


    public long getObjectID()
    {
        return getProperty("ObjectID").getLong();
    }
}

