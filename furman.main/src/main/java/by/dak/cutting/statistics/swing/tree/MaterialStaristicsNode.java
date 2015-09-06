package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: akoyro
 * Date: 20.11.2010
 * Time: 13:01:04
 */
public class MaterialStaristicsNode extends AStatisticsNode
{
    public MaterialStaristicsNode(StatisticFilter filter)
    {
        super(filter);
        setUserObject(getResourceMap().getString("MaterialStaristicsNode.name"));
    }

    @Override
    protected void initChildren()
    {
        MaterialType[] types = MaterialType.parentTypes();
        if (getFilter().getType() != null)
        {
            types = new MaterialType[]{getFilter().getType()};
        }

        for (MaterialType type : types)
        {
            switch (type)
            {
                case board:
                    add(createBoardStatistics());
                    break;
                case border:
                    //должен создаватся каждому свой так как furniture отличается рамером
                    SearchFilter searchFilter = getFilter().getSearchFilter(StatisticFilter.PROPERTY_order);
                    searchFilter.addAscOrder(Border.PROPERTY_priceAware + "." + PriceAware.PROPERTY_name);
                    searchFilter.addAscOrder(Border.PROPERTY_priced + "." + Priced.PROPERTY_name);
                    add(createBorderStatistics(searchFilter));
                    break;
                case furniture:
                    searchFilter = getFilter().getSearchFilter(FurnitureLink.PROPERTY_orderItem + "." +
                            OrderItem.PROPERTY_order);
                    searchFilter.addAscOrder(FurnitureLink.PROPERTY_priceAware + "." + PriceAware.PROPERTY_name);
                    searchFilter.addAscOrder(FurnitureLink.PROPERTY_priced + "." + Priced.PROPERTY_name);
                    add(createFurnitureStatistics(searchFilter));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    private BoardNode createBoardStatistics()
    {
        Map<BoardDef, List<TextureEntity>> pairs = FacadeContext.getStripsFacade().findUniquePairsBy(getFilter());
        return new BoardNode(pairs, getFilter());
    }

    private BorderNode createBorderStatistics(SearchFilter searchFilter)
    {
        List<Border> borderList = FacadeContext.getBorderFacade().loadAll(searchFilter);
        Map<BorderDefEntity, List<TextureEntity>> listMap = new HashMap<BorderDefEntity, List<TextureEntity>>();
        for (Border border : borderList)
        {
            List<TextureEntity> textures = listMap.get(border.getPriceAware());
            if (textures == null)
            {
                textures = new ArrayList<TextureEntity>();
                listMap.put(border.getPriceAware(), textures);
            }
            if (!textures.contains(border.getTexture()))
                textures.add(border.getTexture());
        }
        return new BorderNode(listMap, getFilter());
    }

    private FurnitureNode createFurnitureStatistics(SearchFilter searchFilter)
    {
        List<FurnitureLink> furnitureList = FacadeContext.getFurnitureLinkFacade().loadAll(searchFilter);
        Map<FurnitureType, List<FurnitureCode>> furnitureMap = new HashMap<FurnitureType, List<FurnitureCode>>();
        for (FurnitureLink furniture : furnitureList)
        {
            List<FurnitureCode> furnitures = furnitureMap.get(furniture.getFurnitureType());
            if (furnitures == null)
            {
                furnitures = new ArrayList<FurnitureCode>();
                furnitureMap.put(furniture.getFurnitureType(), furnitures);
            }
            if (!furnitures.contains(furniture.getFurnitureCode()))
                furnitures.add(furniture.getFurnitureCode());
        }
        return new FurnitureNode(furnitureMap, getFilter());
    }
}
