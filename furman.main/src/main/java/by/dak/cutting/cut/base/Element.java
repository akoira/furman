/*
 * Element.java
 *
 * Created on 26. ��jen 2006, 11:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Peca
 */
@XStreamAlias("Element")
public class Element extends Dimension
{
    public static final String PROPERTY_description = "description";

    private static long staticInternalId = 0;
    private Object id;
    private long internalId;
    private boolean rotatable;
    private DimensionItem dimensionItem;

    private boolean rotated;

    private String description;
    private String shortDescription;


    public Element()
    {
    }

    public Element(DimensionItem dimensionItem)
    {
        super(dimensionItem.getWidth(), dimensionItem.getHeight());
        this.dimensionItem = dimensionItem;
        this.id = dimensionItem.getId();
    }

    /**
     * @param width
     * @param height
     * @param id
     */
    public Element(int width, int height, Object id)
    {
        super(width, height);
        this.id = id;
        this.internalId = staticInternalId++;
    }

    public Element(int width, int height)
    {
        this(width, height, null);
    }

    public Object getId()
    {
        return id;
    }

    public void setId(Object id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return String.format("%1s: %2d x%3d", id, getWidth(), getHeight());
    }


    @Override
    public int hashCode()
    {
        int hash = super.hashCode();
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0) + (int) (this.internalId * 13);
        return hash;
    }

    public void setRotatable(boolean rotatable)
    {
        this.rotatable = rotatable;
    }

    public boolean isRotatable()
    {
        return rotatable;
    }

    public String getDescription()
    {
        return description;
    }

    public DimensionItem getDimensionItem()
    {
        return dimensionItem;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public boolean isRotated()
    {
        return rotated;
    }

    public void setRotated(boolean rotated)
    {
        this.rotated = rotated;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }
}
