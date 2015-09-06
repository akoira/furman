package by.dak.cutting.swing.order.data;

import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.Manufacturer;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.TextureEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OrderDetailsDTO
{
    private Manufacturer manufacturer;

    private Long secondNumber;
    private Long number;
    private String name;
    private BoardDef type;
    private TextureEntity texture;
    private Long width;
    private Long length;
    private Integer count;
    private boolean rotatable;
    private Long matLength;
    private Long matWidth;
    private Glueing glueing;
    private String milling;
    private Drilling drilling;
    private Groove groove;
    private A45 a45;
    private Cutoff cutoff;
    private OrderDetailsDTO second;
    //for deletion operation
    private OrderFurniture orderFurniture;
    //for comparing
    private OrderDetailsDTO savedOrderDetails;

    public Long getNumber()
    {
        return number;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BoardDef getBoardDef()
    {
        return type;
    }

    public void setBoardDef(BoardDef type)
    {
        this.type = type;
        updateMatSize();
    }

    private void updateMatSize()
    {
        if (this.type == null)
        {
            matWidth = 0l;
            matLength = 0l;
        }
        else if (matLength == null || matLength == 0
                || matWidth == null || matWidth == 0)
        {
            matLength = this.type.getDefaultLength();
            matWidth = this.type.getDefaultWidth();
        }
    }

    public TextureEntity getTexture()
    {
        return texture;
    }

    public void setTexture(TextureEntity texture)
    {
        this.texture = texture;
    }

    public Long getWidth()
    {
        return width;
    }

    public void setWidth(Long width)
    {
        this.width = width;
    }

    public Long getLength()
    {
        return length;
    }

    public void setLength(Long length)
    {
        this.length = length;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public boolean isRotatable()
    {
        return rotatable;
    }

    public void setRotatable(boolean rotatable)
    {
        this.rotatable = rotatable;
    }

    public Long getMatLength()
    {
        return matLength;
    }

    public void setMatLength(Long max_length)
    {
        this.matLength = max_length;
    }

    public Long getMatWidth()
    {
        return matWidth;
    }

    public void setMatWidth(Long mat_width)
    {
        this.matWidth = mat_width;
    }

    public Glueing getGlueing()
    {
        return glueing;
    }

    public void setGlueing(Glueing glueing)
    {
        this.glueing = glueing;
    }

    public String getMilling()
    {
        return milling;
    }

    public void setMilling(String milling)
    {
        this.milling = milling;
    }

    public Drilling getDrilling()
    {
        return drilling;
    }

    public void setDrilling(Drilling drilling)
    {
        this.drilling = drilling;
    }

    public Groove getGroove()
    {
        return groove;
    }

    public void setGroove(Groove groove)
    {
        this.groove = groove;
    }

    public A45 getA45()
    {
        return a45;
    }

    public void setA45(A45 a45)
    {
        this.a45 = a45;
    }

    public OrderFurniture getOrderFurnitureEntity()
    {
        return orderFurniture;
    }

    public void setOrderFurnitureEntity(OrderFurniture orderFurniture)
    {
        this.orderFurniture = orderFurniture;
    }

    public OrderDetailsDTO getSavedOrderDetails()
    {
        return savedOrderDetails;
    }

    public void updateSavedOrderDetails()
    {
        this.savedOrderDetails = (OrderDetailsDTO) this.clone();
    }

    public OrderDetailsDTO getSecond()
    {
        return second;
    }

    public void setSecond(OrderDetailsDTO second)
    {
        this.second = second;
    }


    @Override
    public Object clone()
    {
        OrderDetailsDTO cloneObj = new OrderDetailsDTO();

        cloneObj.setNumber(getNumber());
        if (getName() != null)
        {
            cloneObj.setName(new String(getName()));
        }
        //todo я убрал клонирование этих объектов так как не вижу надобности в этом, да и не правельно это
        if (getBoardDef() != null)
        {
            cloneObj.setBoardDef(getBoardDef());
        }

        if (getTexture() != null)
        {
            cloneObj.setTexture(getTexture());
        }
        if (getWidth() != null)
        {
            cloneObj.setWidth(new Long(getWidth()));
        }
        if (getLength() != null)
        {
            cloneObj.setLength(new Long(getLength()));
        }
        if (getCount() != null)
        {
            cloneObj.setCount(new Integer(getCount()));
        }
        if (getMatWidth() != null)
        {
            cloneObj.setMatWidth(new Long(getMatWidth()));
        }
        if (getMatLength() != null)
        {
            cloneObj.setMatLength(new Long(getMatLength()));
        }
        if (getGlueing() != null)
        {
            cloneObj.setGlueing((Glueing) getGlueing().clone());
        }
        if (getMilling() != null)
        {
            cloneObj.setMilling(new String(getMilling()));
        }
        if (getDrilling() != null)
        {
            cloneObj.setDrilling((Drilling) getDrilling().clone());
        }
        if (getGroove() != null)
        {
            cloneObj.setGroove((Groove) getGroove().clone());
        }
        if (getA45() != null)
        {
            cloneObj.setA45((A45) getA45().clone());
        }
        if (getCutoff() != null)
        {
            cloneObj.setCutoff((Cutoff) getCutoff().clone());
        }
        cloneObj.setRotatable(new Boolean(isRotatable()));
        return cloneObj;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (obj.getClass() != getClass())
        {
            return false;
        }
        OrderDetailsDTO orderDetails = (OrderDetailsDTO) obj;
        return new EqualsBuilder()
                .append(getNumber(), orderDetails.getNumber())
                .append(getName(), orderDetails.getName())
                .append(getBoardDef(), orderDetails.getBoardDef())
                .append(getTexture(), orderDetails.getTexture())
                .append(getWidth(), orderDetails.getWidth())
                .append(getLength(), orderDetails.getLength())
                .append(getCount(), orderDetails.getCount())
                .append(isRotatable(), orderDetails.isRotatable())
                .append(getMatWidth(), orderDetails.getMatWidth())
                .append(getMatLength(), orderDetails.getMatLength())
                .append(getGlueing(), orderDetails.getGlueing())
                .append(getMilling(), orderDetails.getMilling())
                .append(getDrilling(), orderDetails.getDrilling())
                .append(getGroove(), orderDetails.getGroove())
                .append(getA45(), orderDetails.getA45())
                .append(getCutoff(), orderDetails.getCutoff())
                .isEquals();
    }


    public boolean equalsWithSaved()
    {
        return equals(savedOrderDetails);
    }


    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("number", getNumber()).append("name", getName())/*.append(
                "usertype",
                StringValueAnnotationProcessor.getProcessor().getEntityToStringConverter(BoardDef.class)
                        .convert(getBorderDef())).append(
                "texture",
                StringValueAnnotationProcessor.getProcessor().getEntityToStringConverter(BoardDef.class)
                        .convert(getTexture()))*/.
                append("width", getWidth()).
                append("length", getLength()).
                append("count", getCount()).
                append("rotatable", isRotatable()).
                append("matLength", getMatLength()).
                append("matWidth", getMatWidth()).
                append("glueing", getGlueing()).
                append("milling", getMilling()).
                append("drilling", getDrilling()).
                append("channel", getGroove()).
                append("a45", getA45()).
                append("cutoff", getCutoff()).toString();
    }


    public boolean isNew()
    {
        return orderFurniture == null;
    }

    public Long getSecondNumber()
    {
        return secondNumber;
    }

    public void setSecondNumber(Long secondNumber)
    {
        this.secondNumber = secondNumber;
    }

    public Cutoff getCutoff()
    {
        return cutoff;
    }

    public void setCutoff(Cutoff cutoff)
    {
        this.cutoff = cutoff;
    }

    public Manufacturer getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer)
    {
        this.manufacturer = manufacturer;
    }
}
