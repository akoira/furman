package by.dak.design.draw.components;

import by.dak.design.draw.components.convert.Location2StringConverter;
import by.dak.design.draw.components.convert.Type2StringConverter;
import by.dak.model3d.DBox;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.TextureEntity;
import by.dak.utils.convert.StringValue;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 13.08.11
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class BoardElement extends DBox
{
    public static final String PROPERTY_boardDef = "boardDefinition";
    public static final String PROPERTY_texture = "textureEntity";

    public static final String PROPERTY_overmeasureLength = "overmeasureLength";
    public static final String PROPERTY_overmeasureWidth = "overmeasureWidth";

    public static final String PROPERTY_location = "location";

    public static final String PROPERTY_type = "type";

    public static final String PROPERTY_orderFurniture = "orderFurniture";

    public static final String PROPERTY_numeration = "numeration";

    private BoardDef boardDef;
    private TextureEntity texture;
    /**
     * припуск
     */
    private Long overmeasureLength = 0l;
    private Long overmeasureWidth = 0l;
    private Location location = null;
    private Type type = Type.common;
    private OrderFurniture orderFurniture;
    private Integer numeration = 0;


    public BoardDef getBoardDef()
    {
        return boardDef;
    }


    @Override
    public void setHeight(double height)
    {
    }

    public void setBoardDef(BoardDef boardDef)
    {
        BoardDef old = this.boardDef;
        this.boardDef = boardDef;
        super.setHeight(this.boardDef.getThickness());
        firePropertyChange(PROPERTY_boardDef, old, boardDef);
    }

    public TextureEntity getTexture()
    {
        return texture;
    }

    public void setTexture(TextureEntity texture)
    {
        TextureEntity old = this.texture;
        this.texture = texture;
        firePropertyChange(PROPERTY_texture, old, texture);
    }

    public Long getOvermeasureLength()
    {
        return overmeasureLength;
    }

    public void setOvermeasureLength(Long overmeasureLength)
    {
        Long old = this.overmeasureLength;
        this.overmeasureLength = overmeasureLength;
        firePropertyChange(PROPERTY_overmeasureLength, old, overmeasureLength);
    }

    public Long getOvermeasureWidth()
    {
        return overmeasureWidth;
    }

    public void setOvermeasureWidth(Long overmeasureWidth)
    {
        Long old = this.overmeasureWidth;
        this.overmeasureWidth = overmeasureWidth;
        firePropertyChange(PROPERTY_overmeasureWidth, old, overmeasureWidth);
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        Location old = this.location;
        this.location = location;
        firePropertyChange(PROPERTY_location, old, location);
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        Type old = this.type;
        this.type = type;
        firePropertyChange(PROPERTY_type, old, type);
    }

    public OrderFurniture getOrderFurniture()
    {
        return orderFurniture;
    }

    public void setOrderFurniture(OrderFurniture orderFurniture)
    {
        OrderFurniture old = this.orderFurniture;
        this.orderFurniture = orderFurniture;
        firePropertyChange(PROPERTY_orderFurniture, old, orderFurniture);
    }

    public Integer getNumeration()
    {
        return numeration;
    }

    public void setNumeration(Integer numeration)
    {
        Integer old = this.numeration;
        this.numeration = numeration;
        firePropertyChange(PROPERTY_numeration, old, numeration);
    }

    @Override
    public Object clone()
    {
        BoardElement that = new BoardElement();
        that.setTexture(getTexture());
        that.setBoardDef(getBoardDef());
        that.setLength(getLength());
        that.setWidth(getWidth());
        return that;
    }

    @StringValue(converterClass = Type2StringConverter.class)
    public static enum Type
    {
        common(0d), //обычный
        raised(100d), //фальш
        pannier(100d); //короб

        private double size;

        Type(double size)
        {
            this.size = size;
        }

        public double getSize()
        {
            return size;
        }
    }

    @StringValue(converterClass = Location2StringConverter.class)
    public static enum Location
    {
        top,
        bottom,
        left,
        right
    }

}
