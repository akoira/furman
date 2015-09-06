package by.dak.cutting.swing;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.swing.Constants;
import by.dak.draw.ChildFigure;
import by.dak.persistence.entities.CutDirection;
import by.dak.persistence.entities.Cutter;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.handle.Handle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

/**
 * User: akoyro
 * Date: 15.12.2010
 * Time: 23:46:08
 */
public class ElementFigure extends GraphicalCompositeFigure implements ChildFigure<SegmentFigure>
{
    public final static int DEFAULT_FONT_SIZE = 32;

    public static final String PROPERTY_element = "element";

    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(getClass());

    private ImageIcon rotatedIcon = resourceMap.getImageIcon("rotated");

    private Element element;
    private SegmentFigure parent;
    private boolean rotated = false;

    private Cutter cutter;

    private LengthWidhtGetter lengthWidhtGetter = new LengthWidhtGetter(element, cutter, rotated);


    public ElementFigure()
    {
        setTransformable(false);
        addPropertyChangeListener("element", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                changeElement();
            }
        });
    }

    private void changeElement()
    {
        Figure figure = getPresentationFigure();
        if (figure != null)
        {
            remove(figure);
        }
        GroupFigure groupFigure = new GroupFigure();

        TextAreaFigure textAreaFigure = new TextAreaFigure();

        groupFigure.add(textAreaFigure);
        //groupFigure.add(imageFigure);


        textAreaFigure.setEditable(true);
        if (getElement() != null)
        {
            element.setRotated(isRotated());

            textAreaFigure.set(AttributeKeys.FILL_COLOR, Constants.COLOR_ELEMENT_DISPLAY);
            AElement2StringConverter.setDescriptionsTo(element);
            textAreaFigure.setText(getElement().getDescription());
            textAreaFigure.setFontSize(42);

            Point2D.Double anchor = new Point2D.Double();
            Point2D.Double lead = new Point2D.Double(lengthWidhtGetter.getLength(), lengthWidhtGetter.getWidth());
            textAreaFigure.setBounds(anchor, lead);
        }
        setPresentationFigure(groupFigure);
    }

    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        if (isRotated())
        {
            g.drawImage(rotatedIcon.getImage(),
                    (int) (getBounds().getCenterX() - 32),
                    (int) (getBounds().getCenterY() - 32),
                    64,
                    64,
                    null);
        }
    }

    public Element getElement()
    {
        return element;
    }

    public void setElement(Element element)
    {
        Element old = this.element;
        this.element = element;
        lengthWidhtGetter.element = element;
        firePropertyChange("element", old, this.element);
    }

    @Override
    public Collection<Handle> createHandles(int detailLevel)
    {
        Collection<Handle> handles = super.createHandles(detailLevel);
        switch (detailLevel)
        {
            case 0:
                handles.add(new ElementRotateHandle(this));
                break;
            default:
                break;
        }
        return handles;
    }

    @Override
    public SegmentFigure getParent()
    {
        return parent;
    }

    @Override
    public void setParent(SegmentFigure parent)
    {
        this.parent = parent;
    }

    @Override
    public void transform(AffineTransform tx)
    {
        super.transform(tx);
    }

    @Override
    public int getLayer()
    {
        return 1000;
    }

    @Override
    public boolean handleDrop(Point2D.Double p, Collection<Figure> droppedFigures, DrawingView view)
    {
        return true;
    }

    public boolean isRotated()
    {
        return rotated;
    }


//	@Override
//	public boolean equals(Object obj) {
//		return obj instanceof ElementFigure && ((ElementFigure) obj).getElement() == this.getElement();
//	}

	public void setRotated(boolean rotated) {
        this.rotated = rotated;
        lengthWidhtGetter.rotated = rotated;
    }

    public Cutter getCutter()
    {
        return cutter;
    }

    public void setCutter(Cutter cutter)
    {
        this.cutter = cutter;
        lengthWidhtGetter.cutter = cutter;
    }


    public static class LengthWidhtGetter
    {

        private Cutter cutter;
        private boolean rotated;
        private Element element;

        public LengthWidhtGetter(Element element, Cutter cutter, boolean rotated)
        {
            this.cutter = cutter;
            this.rotated = rotated;
            this.element = element;
        }

        public Double getLength()
        {
            CutDirection direction = cutter != null ? cutter.getDirection() : CutDirection.horizontal;

            switch (direction)
            {
                case horizontal:
                    return new Double(rotated ? element.getWidth() : element.getHeight());
                case vertical:
                    return new Double(rotated ? element.getHeight() : element.getWidth());
                default:
                    throw new IllegalArgumentException();
            }
        }

        public Double getWidth()
        {
            CutDirection direction = cutter != null ? cutter.getDirection() : CutDirection.horizontal;

            switch (direction)
            {
                case horizontal:
                    return new Double(rotated ? element.getHeight() : element.getWidth());
                case vertical:
                    return new Double(rotated ? element.getWidth() : element.getHeight());
                default:
                    throw new IllegalArgumentException();
            }
        }
    }


}
