/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import by.dak.cutting.cut.base.Dimension;

/**
 * @author Peca
 */
public class DimensionItem
{
    private Dimension dimension;
    private Object id;
    private int count;

    protected DimensionItem()
    {
        dimension = new Dimension();
    }

    public DimensionItem(Dimension dimension, Object id, int count)
    {
        this.dimension = dimension;
        this.id = id;
        this.count = count;
    }

    public DimensionItem(int width, int height, Object id, int count)
    {
        this(new Dimension(width, height), id, count);
    }

    public DimensionItem(int width, int height, Object id)
    {
        this(width, height, id, 1);
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public Dimension getDimension()
    {
        return dimension;
    }

    public void setDimension(Dimension dimension)
    {
        this.dimension = dimension;
    }

    public Object getId()
    {
        return id;
    }

    public void setId(Object id)
    {
        this.id = id;
    }

    public int getWidth()
    {
        return this.dimension.getWidth();
    }

    public void setWidth(int width)
    {
        this.dimension.setWidth(width);
    }

    public int getHeight()
    {
        return this.dimension.getHeight();
    }

    public void setHeight(int height)
    {
        this.dimension.setHeight(height);
    }
}
