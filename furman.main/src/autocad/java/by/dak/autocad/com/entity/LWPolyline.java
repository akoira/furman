package by.dak.autocad.com.entity;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * User: akoyro
 * Date: 27.03.11
 * Time: 21:24
 */
public class LWPolyline extends AEntity implements ILength
{
    public LWPolyline(Dispatch dispatchToBeWrapped)
    {
        super(dispatchToBeWrapped);
    }

    public void setClosed(boolean closed)
    {
        this.setProperty("Closed", closed);
    }

    public boolean isClosed()
    {
        return this.invoke("Closed").getBoolean();
    }

    public double getLength()
    {
        return this.getProperty("Length").getDouble();
    }

    public void setBulge(int index, double bulge)
    {
        this.invoke("SetBulge", new Variant(index), new Variant(bulge));
    }

}
