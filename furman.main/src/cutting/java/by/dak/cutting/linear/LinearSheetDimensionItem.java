package by.dak.cutting.linear;

import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.linear.entity.FurnitureXConverter;
import by.dak.persistence.cutting.entities.CutterXConverter;
import by.dak.persistence.cutting.entities.StorageElementLinkXConverter;
import by.dak.persistence.entities.Cutter;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.StorageElementLink;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 01.03.11
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
@XStreamAlias("LinearSheetDimensionItem")
public class LinearSheetDimensionItem extends DimensionItem
{
    public static int SHEET_HEIGHT = 60;

    @XStreamConverter(value = FurnitureXConverter.class)
    private Furniture furniture;

    @XStreamConverter(value = StorageElementLinkXConverter.class)
    private StorageElementLink storageElementLink;

    @XStreamConverter(value = CutterXConverter.class)
    private Cutter cutter;

    /**
     * The constructor needs for correct working with XStream
     */
    protected LinearSheetDimensionItem()
    {
    }

    public LinearSheetDimensionItem(Furniture furniture, Cutter cutter)
    {
        this.cutter = cutter;
        //все материалы добовляются по одному
        setCount(1);
        this.furniture = furniture;
        init(furniture);
    }

    public LinearSheetDimensionItem(StorageElementLink storageElementLink, Cutter cutter)
    {
        this((Furniture) storageElementLink.getStoreElement(), cutter);
        this.storageElementLink = storageElementLink;
    }

    private void init(Furniture furniture)
    {
        setHeight(UnitConverter.convertToMiliMetre(furniture.getSize()));
        setWidth(SHEET_HEIGHT);       //потому что сегменту устанавливается length как width, a width как height
        setId(furniture.getId());
    }

    public StorageElementLink getStorageElementLink()
    {
        return storageElementLink;
    }

    public Cutter getCutter()
    {
        return cutter;
    }

    public void setStorageElementLink(StorageElementLink storageElementLink)
    {
        this.storageElementLink = storageElementLink;
    }

    public Furniture getFurniture()
    {
        return furniture;
    }

    public void setFurniture(Furniture furniture)
    {
        this.furniture = furniture;
    }
}
