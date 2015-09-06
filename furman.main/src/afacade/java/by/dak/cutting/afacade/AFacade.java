package by.dak.cutting.afacade;

import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 23.08.2010
 * Time: 23:20:21
 */
@MappedSuperclass
public abstract class AFacade<T extends AProfileType, C extends AProfileColor> extends OrderItem
{
    public static final String PROPERTY_length = "length";
    public static final String PROPERTY_width = "width";
    public static final String PROPERTY_filling = "filling";
    public static final String PROPERTY_upProfile = "upProfile";
    public static final String PROPERTY_leftProfile = "leftProfile";
    public static final String PROPERTY_profileType = "profileType";
    public static final String PROPERTY_profileColor = "profileColor";
    public static final String PROPERTY_rubber = "rubber";
    public static final String PROPERTY_drilling = "drilling";


    private Double length = 0D;
    private Double width = 0D;

    @Column
    private Integer drilling;

    //заполнение
    @Transient
    private OrderFurniture filling = new OrderFurniture();
    //профили
    @Transient
    private FurnitureLink upProfile = new FurnitureLink();
    @Transient
    private FurnitureLink leftProfile = new FurnitureLink();
    //уплотнитель
    @Transient
    private FurnitureLink rubber = new FurnitureLink();


    @Transient
    private T profileType;
    @Transient
    private C profileColor;


    public FurnitureLink getUpProfile()
    {
        return upProfile;
    }

    public void setUpProfile(FurnitureLink upProfile)
    {
        this.upProfile = upProfile;
    }

    public FurnitureLink getLeftProfile()
    {
        return leftProfile;
    }

    public void setLeftProfile(FurnitureLink leftProfile)
    {
        this.leftProfile = leftProfile;
    }


    public List<AOrderDetail> getTransients()
    {
        ArrayList<AOrderDetail> links = new ArrayList<AOrderDetail>();
        links.add(getFilling());
        links.add(getUpProfile());
        links.add(getLeftProfile());
        links.add(getRubber());
        return links;
    }


    public OrderFurniture getFilling()
    {
        return filling;
    }

    public void setFilling(OrderFurniture filling)
    {
        this.filling = filling;
    }

    public Double getLength()
    {
        return length;
    }

    public void setLength(Double length)
    {
        this.length = length;
    }

    public Double getWidth()
    {
        return width;
    }

    public void setWidth(Double width)
    {
        this.width = width;
    }

    public C getProfileColor()
    {
        return profileColor;
    }

    public void setProfileColor(C profileColor)
    {
        C old = this.profileColor;
        this.profileColor = profileColor;
        support.firePropertyChange(PROPERTY_profileColor, old, profileColor);
    }

    public T getProfileType()
    {
        return profileType;
    }

    public void setProfileType(T profileType)
    {
        T old = this.profileType;
        this.profileType = profileType;
        support.firePropertyChange(PROPERTY_profileType, old, profileType);
    }

    public FurnitureLink getRubber()
    {
        return rubber;
    }

    public void setRubber(FurnitureLink rubber)
    {
        this.rubber = rubber;
    }


    public Integer getDrilling()
    {
        return drilling;
    }

    public void setDrilling(Integer drilling)
    {
        this.drilling = drilling;
    }
}
