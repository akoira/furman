/*
 * Dimension.java
 *
 * Created on 26. jen 2006, 11:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

/**
 * @author Peca
 */
public class Dimension
{

    private int width, height;

    /**
     * Creates a new instance of Dimension
     */
    public Dimension()
    {
        this.width = 0;
        this.height = 0;
    }

    public Dimension(int width, int height)
    {
        this();
        this.width = width;
        this.height = height;
    }

    public Dimension(Dimension dim)
    {
        this();
        this.width = dim.width;
        this.height = dim.height;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public Rect toRect()
    {
        Rect rct = new Rect(0, 0, width, height);
        return rct;
    }

    public int getArea()
    {
        return width * height;
    }

    public Dimension rotate()
    {
        int tmp = this.width;
        this.width = this.height;
        this.height = tmp;
        return this;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 47 * hash + this.width;
        hash = 47 * hash + this.height;
        return hash;
    }

    @Override
    public String toString()
    {
        return String.format("%1d x%2d", width, height);
    }


}
