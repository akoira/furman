package by.dak.autocad.com;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * User: akoyro
 * Date: 27.03.11
 * Time: 21:09
 */
public class AcadState extends ActiveXComponent
{
    public AcadState(Dispatch dispatch)
    {
        super(dispatch);
    }

    public boolean IsQuiescent()
    {
        return invoke("IsQuiescent").getBoolean();
    }
}
