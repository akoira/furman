package by.dak.cutting.swing;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.persistence.entities.Cutter;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Figure;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

/**
 * User: akoyro
 * Date: 18.12.2010
 * Time: 1:20:40
 */
public class CuttingDrawing extends DefaultDrawing
{
    public static final double OFFSET_X = 50d;
    public static final double OFFSET_Y = 50d;

    private Segment segment;
    private SegmentFigure topFigure;

    private Cutter cutter;

    private CuttingDrawingDelegate delegate;


    public CuttingDrawing()
    {
        addPropertyChangeListener("segment", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                segmentChanged();
            }
        });
    }

    /**
     * Ищет только ElemenFigure
     *
     * @param p
     * @return
     */
    @Override
    public Figure findFigure(Point2D.Double p)
    {
        for (Figure f : getFiguresFrontToBack())
        {
            if (f.isVisible() && f.contains(p))
            {
                if (f instanceof SegmentFigure)
                {
                    return ((SegmentFigure) f).findChild(p);
                }
                else
                {
                    return f;
                }
            }
        }
        return null;
    }

    /**
     * Ищет все figure
     *
     * @param p
     * @param ignore
     * @return
     */
    @Override
    public Figure findFigureExcept(Point2D.Double p, Collection<? extends Figure> ignore)
    {
        for (Figure f : getFiguresFrontToBack())
        {
            if (!ignore.contains(f) && f.isVisible() && f.contains(p))
            {
                if (f instanceof SegmentFigure)
                {
                    Figure figure = ((SegmentFigure) f).findChildExcept(p, ignore);
                    if (figure != null)
                    {
                        return figure;
                    }
                }
                return f;
            }
        }
        return null;
    }

    public SegmentFigure getTopFigure()
    {
        return topFigure;
    }

    public Segment getSegment()
    {
        return segment;
    }

    public void setSegment(Segment segment)
    {
        Segment old = this.segment;
        this.segment = segment;
        firePropertyChange("segment", old, segment);
    }

    private void segmentChanged()
    {
        getDrawing().remove(topFigure);
        if (getSegment() != null)
        {
            topFigure = new SegmentFigure();
            topFigure.setCutter(cutter);
            topFigure.setFreeAreaSizeFigureHelper(new BoardFreeAreaSizeHelper());
            topFigure.setSegment(getSegment());
            topFigure.move(topFigure, OFFSET_X, OFFSET_Y);
            topFigure.setSelectable(true);

            getDrawing().add(topFigure);
        }
        if (delegate != null)
            delegate.changed(this);
    }


    public SegmentFigure findSegmentFigure(Segment segment)
    {
        return topFigure != null ? topFigure.findSegmentFigure(segment) : null;
    }

    /**
     * проверяет в drawing элемент, не привязанный к сегменту раскроя
     *
     * @return
     */
    public boolean hasUnboundElements()
    {
        return this.getChildCount() > 1;
    }


    public Cutter getCutter()
    {
        return cutter;
    }

    public void setCutter(Cutter cutter)
    {
        this.cutter = cutter;
    }


    public CuttingDrawingDelegate getDelegate()
    {
        return delegate;
    }

    public void setDelegate(CuttingDrawingDelegate delegate)
    {
        this.delegate = delegate;
    }
}
