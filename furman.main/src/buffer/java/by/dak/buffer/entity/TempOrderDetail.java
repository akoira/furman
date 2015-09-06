package by.dak.buffer.entity;

import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.predefined.Side;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.10.11
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */

/**
 * copy of AOrderDetail
 */
@Table(name = "TEMP_TABLE")
@Entity
public class TempOrderDetail extends PersistenceEntity
{
    @Column(name = "DISCRIMINATOR", nullable = false, updatable = true, insertable = true)
    private String discriminator;

    @Column(name = "NUMBER", nullable = true)
    private Long number = 0L;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name = "";//Constants.DEFAULT_DETAIL_NAME;

    @Column(name = "AMOUNT", nullable = true)
    private Integer amount;

    @Column(name = "SIZE", nullable = true)
    private Double size = 1d;

    @Column(name = "FPRIMARY", nullable = true)
    private Boolean primary;

    @Column(name = "PRICE", nullable = true)
    private Double price;

    @Column(name = "LENGTH", nullable = true)
    private Long length;

    @Column(name = "WIDTH", nullable = true)
    private Long width;

    @Column(name = "ROTATABLE", nullable = true)
    private Boolean rotatable;

    @Column(name = "GLUEING", nullable = true, length = 255)
    private String glueing;

    //фрезеровка
    @Column(name = "MILLING", nullable = true, length = 255)
    private String milling;

    //Паз
    @Column(name = "GROOVE", nullable = true, length = 255)
    private String groove;

    //угол пропила
    @Column(name = "ANGLE45", nullable = true, length = 255)
    private String angle45;

    @Column(name = "DRILLING", nullable = true, length = 255)
    private String drilling;

    @Column(name = "CUTOFF", nullable = true, length = 255)
    private String cutoff;

    @Column(name = "SIDE", nullable = true)
    private Side side = Side.up;

    @Column(name = "TYPE", nullable = true)
    private String type;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = PriceAware.class)
    @JoinColumns({@JoinColumn(name = "FURNITURE_TYPE_ID", nullable = true, referencedColumnName = "ID")})
    private PriceAware furnitureType;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = PriceAware.class)
    @JoinColumns({@JoinColumn(name = "COMLEX_BOARD_DEF_ID", nullable = true, referencedColumnName = "ID")})
    private PriceAware comlexFurnitureType;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = Priced.class)
    @JoinColumns({@JoinColumn(name = "FURNITURE_CODE_ID", nullable = true, referencedColumnName = "ID")})
    private Priced furnitureCode;

    @ManyToOne(targetEntity = OrderFurniture.class)
    @JoinColumns({@JoinColumn(name = "SECOND_BOARD_ID", nullable = true, referencedColumnName = "ID")})
    private OrderFurniture second;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    public Long getOrderItemId()
    {
        return orderItemId;
    }

    public String getDiscriminator()
    {
        return discriminator;
    }

    public Long getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public Double getSize()
    {
        return size;
    }

    public Boolean getPrimary()
    {
        return primary;
    }

    public Double getPrice()
    {
        return price;
    }

    public Long getLength()
    {
        return length;
    }

    public Long getWidth()
    {
        return width;
    }

    public Boolean getRotatable()
    {
        return rotatable;
    }

    public String getGlueing()
    {
        return glueing;
    }

    public String getMilling()
    {
        return milling;
    }

    public String getGroove()
    {
        return groove;
    }

    public String getAngle45()
    {
        return angle45;
    }

    public String getDrilling()
    {
        return drilling;
    }

    public String getCutoff()
    {
        return cutoff;
    }

    public Side getSide()
    {
        return side;
    }

    public String getType()
    {
        return type;
    }

    public PriceAware getFurnitureType()
    {
        return furnitureType;
    }

    public PriceAware getComlexFurnitureType()
    {
        return comlexFurnitureType;
    }

    public Priced getFurnitureCode()
    {
        return furnitureCode;
    }

    public OrderFurniture getSecond()
    {
        return second;
    }

}
