package by.dak.persistence.entities;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;

import javax.persistence.*;

/**
 * User: akoyro
 * Date: 25.09.11
 * Time: 12:38
 */
@MappedSuperclass

public abstract class AOrderBoardDetail extends AOrderDetail<BoardDef, TextureEntity>
{
    public static final String PROPERTY_primary = "primary";
    public static final String PROPERTY_milling = "milling";
    public static final String PROPERTY_rotatable = "rotatable";
    public static final String PROPERTY_boardDef = "boardDef";
    public static final String PROPERTY_texture = "texture";

    public AOrderBoardDetail()
    {
    }

    public AOrderBoardDetail(String name)
    {
        setName(name);
    }


    @Column(name = "LENGTH", nullable = true)
    private Long length;

    @Column(name = "WIDTH", nullable = true)
    private Long width;

    @Column(name = "ROTATABLE", nullable = false, columnDefinition = "bit")
    private boolean rotatable;

    @Column(name = "GLUEING", nullable = true, columnDefinition = "longtext")
    private String glueing;

    //фрезеровка
    @Column(name = "MILLING", nullable = true, columnDefinition = "longtext")
    private String milling;

    //Паз
    @Column(name = "GROOVE", nullable = true, columnDefinition = "longtext")
    private String groove;

    //угол пропила
    @Column(name = "ANGLE45", nullable = true, columnDefinition = "longtext")
    private String angle45;

    @Column(name = "DRILLING", nullable = true, columnDefinition = "longtext")
    private String drilling;

    @Column(name = "CUTOFF", nullable = true, columnDefinition = "longtext")
    private String cutoff;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, targetEntity = BoardDef.class)
    @JoinColumns({@JoinColumn(name = "COMLEX_BOARD_DEF_ID", referencedColumnName = "ID")})
    private BoardDef comlexBoardDef;

    @ManyToOne(cascade = {CascadeType.ALL/*MERGE, CascadeType.PERSIST, CascadeType.REFRESH*/}, targetEntity = OrderFurniture.class)
    @JoinColumns({@JoinColumn(name = "SECOND_BOARD_ID", referencedColumnName = "ID")})
    private OrderFurniture second;

    @Column(name = "FPRIMARY", nullable = false, columnDefinition = "bit")
    private Boolean primary = Boolean.TRUE;

    public void setLength(Long length)
    {
        this.length = length;
    }

    public Long getLength()
    {
        return length;
    }

    public void setWidth(Long width)
    {
        this.width = width;
    }

    public Long getWidth()
    {
        return width;
    }

    public void setRotatable(boolean rotatable)
    {
        this.rotatable = rotatable;
    }

    public boolean isRotatable()
    {
        return rotatable;
    }

    public void setGlueing(String glueing)
    {
        this.glueing = glueing;
    }

    public String getGlueing()
    {
        return glueing;
    }

    public void setMilling(String milling)
    {
        this.milling = milling;
    }

    public String getMilling()
    {
        return milling;
    }

    public void setGroove(String groove)
    {
        this.groove = groove;
    }

    public String getGroove()
    {
        return groove;
    }

    public void setAngle45(String angle45)
    {
        this.angle45 = angle45;
    }

    public String getAngle45()
    {
        return angle45;
    }

    public void setDrilling(String drilling)
    {
        this.drilling = drilling;
    }

    public String getDrilling()
    {
        return drilling;
    }

    public void setBoardDef(BoardDef boardDef)
    {
        setPriceAware(boardDef);
    }

    public BoardDef getBoardDef()
    {
        return getPriceAware();
    }

    public BoardDef getComlexBoardDef()
    {
        return comlexBoardDef;
    }

    public void setComlexBoardDef(BoardDef comlexBoardDef)
    {
        this.comlexBoardDef = comlexBoardDef;
    }

    public void setTexture(TextureEntity texture)
    {
        setPriced(texture);
    }

    public TextureEntity getTexture()
    {
        return getPriced();
    }

    public OrderFurniture getSecond()
    {
        return second;
    }

    public void setSecond(OrderFurniture second)
    {
        this.second = second;
    }


    public void setPrimary(Boolean primary)
    {
        this.primary = primary;
    }

    /**
     * Это поле true для всех кроме второй составной части сложной детали
     *
     * @return
     */
    public Boolean isPrimary()
    {
        return primary;
    }

    public Boolean isComplex()
    {
        return getComlexBoardDef() != null;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (getName() == null ? 0 : getName().hashCode());
        result = prime * result + (getBoardDef() == null ? 0 : getBoardDef().hashCode());
        result = prime * result + (getTexture() == null ? 0 : getTexture().hashCode());
        result = prime * result + (length == null ? 0 : length.hashCode());
        result = prime * result + (width == null ? 0 : width.hashCode());
        result = prime * result + (getAmount() == null ? 0 : getAmount().hashCode());
        result = prime * result + (glueing == null ? 0 : glueing.hashCode());
        result = prime * result + (milling == null ? 0 : milling.hashCode());
        result = prime * result + (groove == null ? 0 : groove.hashCode());
        result = prime * result + (angle45 == null ? 0 : angle45.hashCode());
        result = prime * result + (drilling == null ? 0 : drilling.hashCode());
        return result;
    }


    public String getCutoff()
    {
        return cutoff;
    }

    public void setCutoff(String cutoff)
    {
        this.cutoff = cutoff;
    }

    public TextureBoardDefPair getPair()
    {
        return new TextureBoardDefPair(getTexture(), getBoardDef());
    }

    public Dimension getDimension()
    {
        return new Dimension(getLength().intValue(), getWidth().intValue());
    }

    @Override
    public void clear()
    {
        setBoardDef(null);
        setTexture(null);
    }


    public Object clone()
    {

        try
        {
            AOrderBoardDetail aNew = this.getClass().getConstructor().newInstance();
            aNew.setPrimary(this.isPrimary());

            aNew.setNumber(this.getNumber());
            aNew.setName(this.getName());

            aNew.setBoardDef(this.getBoardDef());
            aNew.setComlexBoardDef(this.getComlexBoardDef());
            aNew.setTexture(this.getTexture());

            aNew.setLength(this.getLength());
            aNew.setWidth(this.getWidth());
            aNew.setAmount(this.getAmount());
            aNew.setRotatable(this.isRotatable());


            aNew.setDrilling(this.getDrilling());
            aNew.setGlueing(this.getGlueing());
            aNew.setGroove(this.getGroove());
            aNew.setMilling(this.getMilling());
            aNew.setAngle45(this.getAngle45());
            aNew.setCutoff(this.getCutoff());
            return aNew;
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
    }


}
