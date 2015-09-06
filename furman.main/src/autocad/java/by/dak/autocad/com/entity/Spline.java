package by.dak.autocad.com.entity;

import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 10.05.11
 * Time: 12:16
 */
public class Spline extends AEntity implements ILength
{
    public Spline(Dispatch dispatch)
    {
        super(dispatch);
        setCurve(true);
    }


    @Override
    public double getLength()
    {
        Double value = 0d;
        //getApplication().getActiveDocument().SetVariable("USERR1", value);
        getApplication().getActiveDocument().SendCommand("(vl-load-com)\n");
        String strCom = "(setvar \"USERR1\" (vlax-curve-getdistatparam (vlax-ename->vla-object (handent \"" + getHandle() + "\")) " +
                "(vlax-curve-getendparam (vlax-ename->vla-object (handent \"" + getHandle() + "\")))))\n";
        getApplication().getActiveDocument().SendCommand(strCom);
        value = getApplication().getActiveDocument().GetVariable("USERR1").getDouble();
        return value;
    }
}
