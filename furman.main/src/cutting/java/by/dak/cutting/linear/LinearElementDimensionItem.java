package by.dak.cutting.linear;

import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.linear.entity.FurnitureLinkXConverter;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.utils.convert.StringValue;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 01.03.11
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
@XStreamAlias("LinearElementDimensionItem")
@StringValue(converterClass = FurnitureElement2StringConverter.class)
public class LinearElementDimensionItem extends DimensionItem
{
    public static int ELEMENT_HEIGHT = 60;
    private long number;//служит номером детали. используется для отображения номера детали на картах раскроя

    @XStreamConverter(value = FurnitureLinkXConverter.class)
    private FurnitureLink furnitureLink;


    /**
     * The constructor needs for correct working with XStream
     */
    public LinearElementDimensionItem()
    {
    }

    public LinearElementDimensionItem(FurnitureLink furnitureLink)
    {
        this.furnitureLink = furnitureLink;
        setCount(furnitureLink.getAmount() * furnitureLink.getOrderItem().getAmount());
        setWidth(ELEMENT_HEIGHT);
        setHeight((int) (furnitureLink.getSize() * 1000));
        setId(String.valueOf(furnitureLink.getId()));
    }

    public FurnitureLink getFurnitureLink()
    {
        return furnitureLink;
    }

    public long getNumber()
    {
        return number;
    }

    public void setNumber(long number)
    {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        LinearElementDimensionItem that = (LinearElementDimensionItem) obj;
        if (!that.getId().equals(this.getId()))
        {
            return false;
        }
        if (!that.getFurnitureLink().equals(this.getFurnitureLink()))
        {
            return false;
        }
        return true;
    }
}
