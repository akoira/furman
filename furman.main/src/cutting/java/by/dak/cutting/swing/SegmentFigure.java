package by.dak.cutting.swing;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.draw.ChildFigure;
import by.dak.persistence.entities.CutDirection;
import by.dak.persistence.entities.Cutter;
import org.jhotdraw.draw.*;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

/**
 * User: akoyro
 * Date: 15.12.2010
 * Time: 16:26:39
 */
public class SegmentFigure extends GraphicalCompositeFigure implements ChildFigure<SegmentFigure>
{
    private Segment segment;
    private SegmentFigure parent;
    private FreeAreaSizeFigure freeAreaSizeFigureHelper;
    private Cutter cutter;


    private LengthWidhtGetter lengthWidhtGetter = new LengthWidhtGetter();

    public SegmentFigure()
    {
        setSelectable(false);
        setTransformable(false);
        addPropertyChangeListener("segment", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                segmentChanged();
            }
        });
    }

    public FreeAreaSizeFigure getFreeAreaSizeFigureHelper()
    {
        return freeAreaSizeFigureHelper;
    }

    public void setFreeAreaSizeFigureHelper(FreeAreaSizeFigure freeAreaSizeFigureHelper)
    {
        this.freeAreaSizeFigureHelper = freeAreaSizeFigureHelper;
    }

    private void segmentChanged()
    {
        lengthWidhtGetter.setSegment(segment);
        Figure figure = getPresentationFigure();
        if (figure != null)
        {
            remove(figure);
        }

        GroupFigure groupFigure = new GroupFigure();
        RectangleFigure rectangleFigure = new RectangleFigure();
        if (getSegment() != null)
        {
            Point2D.Double anchor = new Point2D.Double();
            Point2D.Double lead = new Point2D.Double(lengthWidhtGetter.getLength(), lengthWidhtGetter.getWidth());
            rectangleFigure.setBounds(anchor, lead);
            rectangleFigure.set(AttributeKeys.FILL_COLOR, segment.getSegmentType().getColor());
            groupFigure.add(rectangleFigure);
            if (getSegment().getElement() != null)
            {
                ElementFigure elementFigure = new ElementFigure();
                elementFigure.setCutter(cutter);
                elementFigure.setRotated(getSegment().isRotateElement());
                elementFigure.setElement(getSegment().getElement());
                add(elementFigure);
            }


            initFigureItems();

            freeAreaSizeFigureHelper.createFreeAreaSizeFigure(groupFigure, this);

            setPresentationFigure(groupFigure);
        }
    }

    private void initFigureItems()
    {
        java.util.List<Segment> segments = getSegment().getItems();
        double offset = 0;
        freeAreaSizeFigureHelper = getFreeAreaSizeFigureHelper();
        for (Segment item : segments)
        {
            SegmentFigure figure = new SegmentFigure();
            figure.setCutter(getCutter());
            figure.setFreeAreaSizeFigureHelper(freeAreaSizeFigureHelper);
            figure.setSegment(item);
            add(figure);

            double dx = 0;
            double dy = 0;
            CutDirection direction = cutter != null ? cutter.getDirection() : CutDirection.horizontal;
            switch (direction)
            {

                case horizontal:
                    dx = getSegment().getLevel() % 2 == 0 ? 0d : offset;
                    dy = getSegment().getLevel() % 2 == 0 ? offset : 0d;
                    break;
                case vertical:
                    dy = getSegment().getLevel() % 2 == 0 ? 0d : offset;
                    dx = getSegment().getLevel() % 2 == 0 ? offset : 0d;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            move(figure, dx, dy);
            offset += item.getWidth() + item.getPadding();
        }


    }

    public void move(Figure figure, Double dX, Double dY)
    {
        Point2D.Double start = new Point2D.Double(figure.getBounds().getMinX() + dX, figure.getBounds().getMinX() + dY);
        Point2D.Double end = new Point2D.Double(figure.getBounds().getMaxX() + dX, figure.getBounds().getMaxY() + dY);
        figure.setBounds(start, end);
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

    public Figure getFreeFigure()
    {
        SegmentFigure segmentFigure = new SegmentFigure();
        Segment segment = new Segment(getSegment().getFreeLength(), getSegment().getWidth());
        segment.setParent(getSegment());
        segmentFigure.setSegment(segment);
        return segmentFigure;
    }

    public SegmentFigure getParent()
    {
        return parent;
    }

    public void setParent(SegmentFigure parent)
    {
        this.parent = parent;
    }

    public Figure findChild(Point2D.Double p)
    {
        if (getBounds().contains(p))
        {
            for (Figure child : getChildrenFrontToBack())
            {
                if (child instanceof SegmentFigure)
                {
                    Figure result = ((SegmentFigure) child).findChild(p);
                    if (result != null)
                    {
                        return result;
                    }
                }
                else if (child.isVisible() && child.contains(p))
                {
                    return child;
                }
            }
        }
        return null;
    }


    public Figure findChildExcept(Point2D.Double p, Collection<? extends Figure> ignore)
    {
        if (getBounds().contains(p))
        {
            for (Figure f : getChildrenFrontToBack())
            {
                if (!ignore.contains(f))
                {
                    if (f instanceof SegmentFigure)
                    {
                        Figure result = ((SegmentFigure) f).findChildExcept(p, ignore);
						return result;
					}
                    else if (f.isVisible() && f.contains(p))
                    {
                        return f;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean handleDrop(Point2D.Double p, Collection<Figure> droppedFigures, DrawingView view)
    {
        return true;
    }

    public SegmentFigure findSegmentFigure(Segment segment)
    {
        if (getSegment() == segment)
        {
            return this;
        }
        else
        {
            for (Figure child : getChildrenFrontToBack())
            {
                if (child instanceof SegmentFigure)
                {
                    SegmentFigure result = ((SegmentFigure) child).
                            findSegmentFigure(segment);
                    if (result != null)
                    {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean remove(final Figure figure)
    {
        boolean result = super.remove(figure);
        if (result && figure instanceof ChildFigure)
        {
            ((ChildFigure) figure).setParent(null);
        }
        return result;
    }

    @Override
    public boolean add(Figure figure)
    {
        boolean result = super.add(figure);
        if (result && figure instanceof ChildFigure)
        {
            ((ChildFigure) figure).setParent(this);
        }
        return result;
    }

    public Cutter getCutter()
    {
        return cutter;
    }

    public void setCutter(Cutter cutter)
    {
        this.cutter = cutter;
        lengthWidhtGetter.setCutter(cutter);
    }

    public static class LengthWidhtGetter
    {
        private Segment segment;
        private Cutter cutter;


        public double getLength()
        {
            CutDirection direction = cutter != null ? cutter.getDirection() : CutDirection.horizontal;
            switch (direction)
            {
                case horizontal:
                    return segment.getLevel() % 2 > 0 ? segment.getLength() : segment.getWidth();
                case vertical:
                    return segment.getLevel() % 2 > 0 ? segment.getWidth() : segment.getLength();
                default:
                    throw new IllegalArgumentException();
            }
        }

        public double getWidth()
        {
            CutDirection direction = cutter != null ? cutter.getDirection() : CutDirection.horizontal;
            switch (direction)
            {
                case horizontal:
                    return segment.getLevel() % 2 > 0 ? segment.getWidth() : segment.getLength();
                case vertical:
                    return segment.getLevel() % 2 > 0 ? segment.getLength() : segment.getWidth();
                default:
                    throw new IllegalArgumentException();
            }
        }

        public Segment getSegment()
        {
            return segment;
        }

        public void setSegment(Segment segment)
        {
            this.segment = segment;
        }

        public Cutter getCutter()
        {
            return cutter;
        }

        public void setCutter(Cutter cutter)
        {
            this.cutter = cutter;
        }

    }
}
