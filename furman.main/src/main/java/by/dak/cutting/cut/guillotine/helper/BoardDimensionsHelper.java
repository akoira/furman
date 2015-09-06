package by.dak.cutting.cut.guillotine.helper;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.RestBoardDimension;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.AElement2StringConverter;
import by.dak.cutting.swing.cut.ElementDimensionItem;
import by.dak.cutting.swing.cut.SheetDimentionItem;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.StripsEntity;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.StorageElementLink;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 26.02.11
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
public class BoardDimensionsHelper implements DimensionsHelper<AOrderBoardDetail, RestBoardDimension>
{

    private List<RestBoardDimension> getFreeDimensions(Board board, Segment segment)
    {
        ArrayList<RestBoardDimension> dimensions = new ArrayList<RestBoardDimension>();
        int free = segment.getFreeLength() - segment.getPadding();
        if (free > 0)
        {
            RestBoardDimension dimension = new RestBoardDimension((segment.getLevel() % 2) == 0 ? segment.getWidth() : free,
                    (segment.getLevel() % 2) == 0 ? free : segment.getWidth(), board);
            dimensions.add(dimension);
        }
        for (Segment s : segment.getItems())
        {
            dimensions.addAll(getFreeDimensions(board, s));
        }
        return dimensions;
    }

    @Override
    public List<RestBoardDimension> getRestDimensions(Strips strips)
    {
        ArrayList<RestBoardDimension> dimensions = new ArrayList<RestBoardDimension>();
        for (Segment segment : strips.getItems())
        {
            if (segment.getElements().length > 0)
            {
                StorageElementLink storageElementLink = FacadeContext.getBoardFacade().getStorageElementLinkBy(segment);
                Board board = storageElementLink != null ? (Board) storageElementLink.getStoreElement() : ((SheetDimentionItem) segment.getDimensionItem()).getBoard();
                dimensions.addAll(getFreeDimensions(board, segment));
            }
        }
        return dimensions;
    }

    @Override
    public boolean isWhole(Segment segment)
    {
        DimensionItem dimensionItem = segment.getDimensionItem();
        if (dimensionItem instanceof SheetDimentionItem)
        {
            return ((SheetDimentionItem) dimensionItem).getBoard().isWhole();
        }
        return false;
    }

    @Override
    public AOrderBoardDetail getElementOrderDetail(Element element)
    {
        DimensionItem dimensionItem = element.getDimensionItem();
        if (dimensionItem instanceof ElementDimensionItem)
        {
            return ((ElementDimensionItem) dimensionItem).getFurniture();
        }
        return null;
    }

    @Override
    public void setElementOrderDetail(Element element, AOrderBoardDetail orderFurniture)
    {
        DimensionItem dimensionItem = element.getDimensionItem();
        if (dimensionItem instanceof ElementDimensionItem)
        {
            ((ElementDimensionItem) dimensionItem).setFurniture(orderFurniture);
        }
    }

    @Override
    public void fillWithDimensionItem(Segment segment, DimensionItem dimensionItem)
    {
        StorageElementLink storageElementLink = ((SheetDimentionItem) dimensionItem).getStorageElementLink();
        Board board = (Board) storageElementLink.getStoreElement();
        segment.setMaterialLength(board.getLength());
        segment.setMaterialWidth(board.getWidth());

        int length = FacadeContext.getCutterFacade().trim(dimensionItem.getDimension(), board.getBoardDef().getCutter()).getWidth();
        int width = FacadeContext.getCutterFacade().trim(dimensionItem.getDimension(), board.getBoardDef().getCutter()).getHeight();

        if (storageElementLink.getStripsEntity() instanceof StripsEntity)
        {
            length -= board.getBoardDef().getReservedLength().intValue();
            width -= board.getBoardDef().getReservedLength().intValue();
        }

        segment.setLength(length);
        segment.setWidth(width);
    }

    @Override
    public void fillWithDimensionItem(Element element, DimensionItem dimensionItem)
    {
        element.setRotatable(((ElementDimensionItem) dimensionItem).isRotatable());
        AElement2StringConverter.setDescriptionsTo(element);
    }

    @Override
    public boolean isUseful(Dimension dimension)
    {
        return false;
    }

}
