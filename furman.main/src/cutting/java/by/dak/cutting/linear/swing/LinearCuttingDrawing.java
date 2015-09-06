package by.dak.cutting.linear.swing;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.linear.FurnitureFreeAreaSizeFigureHelper;
import by.dak.cutting.swing.SegmentFigure;
import org.jhotdraw.draw.DefaultDrawing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 16.02.11
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class LinearCuttingDrawing extends DefaultDrawing
{
    private Strips strips;
    public static final Double OFFSET_X = 50d;
    public static final Double OFFSET_Y = 50d;

    public LinearCuttingDrawing()
    {
        addPropertyChangeListener("strips", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                stripsChanged();
            }
        });
    }

    private void stripsChanged()
    {
        List<Segment> segments = strips.getSegments();
        double offsetY = OFFSET_Y;

        removeAllChildren();
        for (Segment segment : segments)
        {
            SegmentFigure segmentFigure = new SegmentFigure();
            segmentFigure.setFreeAreaSizeFigureHelper(new FurnitureFreeAreaSizeFigureHelper());
            segmentFigure.setSegment(segment);
            segmentFigure.move(segmentFigure, OFFSET_X, offsetY);  //по х один и тот же отступ, а по у надо взять длину сегмента и прибавлять отступ
            offsetY += segment.getLength() + OFFSET_Y;

            add(segmentFigure);
        }
    }


    public Strips getStrips()
    {
        return strips;
    }

    public void setStrips(Strips strips)
    {
        Strips old = this.strips;
        this.strips = strips;
        firePropertyChange("strips", old, strips);
    }
}
