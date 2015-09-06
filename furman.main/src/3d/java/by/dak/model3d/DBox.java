package by.dak.model3d;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jdesktop.beans.AbstractBean;

import java.awt.geom.Point2D;

/**
 * User: akoyro
 * Date: 04.09.11
 * Time: 15:18
 */
public class DBox extends AbstractBean
{
    public static final String PROPERTY_boxDivideHandler = "boxDivideHandler";
    public static final String PROPERTY_parent = "parent";
    public static final String PROPERTY_boxLocation = "boxLocation";
    public static final String PROPERTY_x = "x";
    public static final String PROPERTY_y = "y";
    public static final String PROPERTY_z = "z";
    public static final String PROPERTY_length = "length";
    public static final String PROPERTY_height = "height";
    public static final String PROPERTY_width = "width";
    public static final String PROPERTY_angleX = "angleX";
    public static final String PROPERTY_angleY = "angleY";
    public static final String PROPERTY_angleZ = "angleZ";


    private DBoxDivideHandler boxDivideHandler = new DBoxDivideHandler();
    private DBox parent;
    private DBoxLocation boxLocation;

    //center
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double length = 0;
    private double height = 0;
    private double width = 0;

    /**
     * |-----|
     */
    private double angleX = 0;
    private double angleY = 0;
    private double angleZ = 0;


    public DBox()
    {
        boxDivideHandler.setParent(this);
    }

    public Point2D.Double getStartPointXY()
    {
        return new Point2D.Double(getMinX(), getMinY());
    }

    public Point2D.Double getEndPointXY()
    {
        return new Point2D.Double(getMaxX(), getMaxY());
    }


    public double getMinX()
    {
        return (getX() - getLength() / 2.0f);
    }

    public double getMinY()
    {
        return (getY() - getHeight() / 2.0f);
    }

    public double getMinZ()
    {
        return getZ() - getWidth() / 2.f;
    }


    public double getMaxX()
    {
        return (getX() + getLength() / 2.0f);
    }

    public double getMaxY()
    {
        return (getY() + getHeight() / 2.0f);
    }

    public double getMaxZ()
    {
        return (getZ() + getWidth() / 2.f);
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        double old = this.x;
        this.x = x;
        firePropertyChange(PROPERTY_x, old, x);
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        double old = this.y;
        this.y = y;
        firePropertyChange(PROPERTY_y, old, y);
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        double old = this.z;
        this.z = z;
        firePropertyChange(PROPERTY_z, old, z);
    }

    public double getLength()
    {
        return length;
    }

    public void setLength(double length)
    {
        double old = this.length;
        this.length = length;
        firePropertyChange(PROPERTY_length, old, length);
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double height)
    {
        double old = this.height;
        this.height = height;
        firePropertyChange(PROPERTY_height, old, height);
    }

    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        double old = this.width;
        this.width = width;
        firePropertyChange(PROPERTY_width, old, width);
    }

    public DBox getParent()
    {
        return parent;
    }

    public void setParent(DBox parent)
    {
        DBox old = this.parent;
        this.parent = parent;
        firePropertyChange(PROPERTY_parent, old, parent);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    public double getAngleX()
    {
        return angleX;
    }

    public void setAngleX(double angleX)
    {
        double old = this.angleX;
        this.angleX = angleX;
        firePropertyChange(PROPERTY_angleX, old, angleX);
    }

    public double getAngleY()
    {
        return angleY;
    }

    public void setAngleY(double angleY)
    {
        double old = this.angleY;
        this.angleY = angleY;
        firePropertyChange(PROPERTY_angleY, old, angleY);
    }

    public double getAngleZ()
    {
        return angleZ;
    }

    public void setAngleZ(double angleZ)
    {
        double old = this.angleZ;
        this.angleZ = angleZ;
        firePropertyChange(PROPERTY_angleZ, old, angleZ);
    }

    public DBoxDivideHandler getBoxDivideHandler()
    {
        return boxDivideHandler;
    }

    public void setBoxDivideHandler(DBoxDivideHandler boxDivideHandler)
    {
        DBoxDivideHandler old = this.boxDivideHandler;
        this.boxDivideHandler = boxDivideHandler;
        firePropertyChange(PROPERTY_boxDivideHandler, old, boxDivideHandler);
    }


    public Object clone()
    {
        DBox dBox = new DBox();
        dBox.setX(x);
        dBox.setY(y);
        dBox.setZ(z);

        dBox.setLength(length);
        dBox.setHeight(height);
        dBox.setWidth(width);

        dBox.setAngleX(angleX);
        dBox.setAngleY(angleY);
        dBox.setAngleZ(angleZ);
        return dBox;
    }

    public DBox getRotatedBox()
    {
        DBox result = (DBox) clone();
        if (result.getAngleZ() > 0)
        {
            result.setHeight(getLength());
            result.setLength(getHeight());
        }
        result.setAngleX(0);
        result.setAngleX(0);
        result.setAngleX(0);
        return result;
    }

    public DBoxLocation getBoxLocation()
    {
        return boxLocation;
    }

    public void setBoxLocation(DBoxLocation boxLocation)
    {
        DBoxLocation old = this.boxLocation;
        this.boxLocation = boxLocation;
        firePropertyChange(PROPERTY_boxLocation, old, boxLocation);
    }
}
