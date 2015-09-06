package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.handle.MoveHandle;
import org.jhotdraw.draw.locator.Locator;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 24.7.2009
 * Time: 12.58.16
 * To change this template use File | Settings | File Templates.
 */
public class MoveDirectionHandle extends MoveHandle
{
    public MoveDirectionHandle(CurveFigure lineFigure, Locator locator)
    {
        super(lineFigure, locator);
    }


    @Override
    public void trackStep(Point anchor, Point lead, int modifiersEx)
    {
        double value = lead.getY() - anchor.getY();
        if (((CurveFigure) getOwner()).getAngle() == 90)
        {
            value = lead.getX() - anchor.getX();
        }
        else if (((CurveFigure) getOwner()).getAngle() == -90)
        {
            value = anchor.getX() - lead.getX();
        }

        getOwner().willChange();
        ((CurveFigure) getOwner()).setDirection(value > 0 ? 1 : -1);
        getOwner().changed();
    }
}
