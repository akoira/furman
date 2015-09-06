package by.dak.cutting.swing.cut;

import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.swing.BoardElementStringConverter;
import by.dak.persistence.cutting.entities.AOrderBoardDetailXConverter;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.persistence.entities.Cutter;
import by.dak.utils.convert.StringValue;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * User: admin
 * Date: 11.03.2009
 * Time: 16:18:08
 */
@XStreamAlias("ElementDimensionItem")
@StringValue(converterClass = BoardElementStringConverter.class)
public class ElementDimensionItem extends DimensionItem
{
    @XStreamConverter(value = AOrderBoardDetailXConverter.class)
    private AOrderBoardDetail furniture;

    public ElementDimensionItem()
    {
    }

    public ElementDimensionItem(AOrderBoardDetail furniture, Cutter cutter)
    {
        this.furniture = furniture;
        setCount(furniture.getAmount().intValue() * furniture.getOrderItem().getAmount());
        switch (cutter.getDirection())
        {

            case horizontal:
                setWidth(furniture.getWidth().intValue());
                setHeight(furniture.getLength().intValue());
                break;
            case vertical:
                setWidth(furniture.getLength().intValue());
                setHeight(furniture.getWidth().intValue());
                break;
            default:
                throw new IllegalArgumentException();
        }
        setId(String.valueOf(furniture.getId()));
    }


    public AOrderBoardDetail getFurniture()
    {
        return furniture;
    }

    public void setFurniture(AOrderBoardDetail furniture)
    {
        this.furniture = furniture;
    }

    public boolean isRotatable()
    {
        return getFurniture() != null ? getFurniture().isRotatable() : false;
    }
}
