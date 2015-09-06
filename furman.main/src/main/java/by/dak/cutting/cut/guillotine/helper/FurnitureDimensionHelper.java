package by.dak.cutting.cut.guillotine.helper;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.SegmentType;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.linear.LinearElementDimensionItem;
import by.dak.cutting.linear.LinearSheetDimensionItem;
import by.dak.cutting.linear.RestFurnitureDimension;
import by.dak.cutting.linear.UnitConverter;
import by.dak.cutting.swing.AElement2StringConverter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.FurnitureLink;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 02.03.11
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureDimensionHelper implements DimensionsHelper<FurnitureLink, RestFurnitureDimension>
{
    public static double MIN_USEFUL_SIZE = 0.4;

    private Segment findRedSegment(Segment segment)
    {
        for (Segment s : segment.getItems())
        {
            if (s.getSegmentType().equals(SegmentType.red))
            {
                return s;
            }
            else
            {
                findRedSegment(s);
            }
        }
        return null;
    }

    @Override
    public List<RestFurnitureDimension> getRestDimensions(Strips strips)
    {
        List<RestFurnitureDimension> dimensions = new ArrayList<RestFurnitureDimension>();
        for (Segment segment : strips.getItems())
        {
            Furniture furniture = ((LinearSheetDimensionItem) segment.getDimensionItem()).getFurniture();
            Segment redSegment = findRedSegment(segment);
            if (redSegment != null && furniture != null)
            {
                RestFurnitureDimension restFurnitureDimension =
                        new RestFurnitureDimension(redSegment.getFreeLength() - redSegment.getPadding(), segment.getWidth(), furniture);
                dimensions.add(restFurnitureDimension);
            }
        }
        return dimensions;
    }

    @Override
    public boolean isWhole(Segment segment)
    {
        return false;
    }

    @Override
    public FurnitureLink getElementOrderDetail(Element element)
    {
        DimensionItem dimensionItem = element.getDimensionItem();
        if (dimensionItem instanceof LinearElementDimensionItem)
        {
            return ((LinearElementDimensionItem) dimensionItem).getFurnitureLink();
        }
        return null;
    }

    @Override
    public void setElementOrderDetail(Element element, FurnitureLink detail)
    {
    }

    @Override
    public void fillWithDimensionItem(Segment segment, DimensionItem dimensionItem)
    {
        segment.setMaterialLength(dimensionItem.getWidth());
        double size = ((Furniture) ((LinearSheetDimensionItem) dimensionItem).getStorageElementLink().getStoreElement()).getSize();
        segment.setMaterialWidth(UnitConverter.convertToMiliMetre(size));
        segment.setLength(FacadeContext.getCutterFacade().trim(dimensionItem.getDimension(), ((LinearSheetDimensionItem) dimensionItem).getCutter()).getWidth());
        segment.setWidth(FacadeContext.getCutterFacade().trim(dimensionItem.getDimension(), ((LinearSheetDimensionItem) dimensionItem).getCutter()).getHeight());
    }

    @Override
    public void fillWithDimensionItem(Element element, DimensionItem dimensionItem)
    {
        element.setRotatable(false);
        AElement2StringConverter.setDescriptionsTo(element);
    }

    @Override
    public boolean isUseful(Dimension dimension)
    {
        double restSize = UnitConverter.convertToMetre(dimension.getWidth());
        if (restSize >= MIN_USEFUL_SIZE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
