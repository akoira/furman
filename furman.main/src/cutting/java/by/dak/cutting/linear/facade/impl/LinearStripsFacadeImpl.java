package by.dak.cutting.linear.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.cut.facade.impl.AStripsFacadeImpl;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.linear.FurnitureTypeCodePair;
import by.dak.cutting.linear.LinearCuttingModel;
import by.dak.cutting.linear.LinearSheetDimensionItem;
import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.cutting.linear.facade.LinearStripsFacade;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.StripsXmlSerializer;
import by.dak.persistence.entities.Furniture;
import by.dak.persistence.entities.StorageElementLink;

import javax.persistence.DiscriminatorValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 06.05.11
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
public class LinearStripsFacadeImpl extends AStripsFacadeImpl<LinearStripsEntity> implements LinearStripsFacade
{
    private void removeUnusedSegments(Strips strips)
    {
        HashMap<StorageElementLink, Integer> usedFurnitureMap = new HashMap<StorageElementLink, Integer>();  //хранит те палки, которые используются в раскрое и кол-во этих палок
        List<Segment> segments = strips.getSegments();

        for (Segment segment : segments)
        {
            StorageElementLink storageElementLink = ((LinearSheetDimensionItem) segment.getDimensionItem()).getStorageElementLink();
            Integer usedCount = usedFurnitureMap.get(storageElementLink);
            if (usedCount != null)
            {
                usedFurnitureMap.put(storageElementLink, usedCount + 1);
            }
            else
            {
                usedFurnitureMap.put(storageElementLink, 1);
            }
        }
        List<StorageElementLink> storageElementLinks = new ArrayList<StorageElementLink>();

        storageElementLinks.addAll(FacadeContext.getStorageElementLinkFacade().
                loadAllBy(strips.getStripsEntity(), Furniture.class.getAnnotation(DiscriminatorValue.class).value()));

        for (StorageElementLink storageElementLink : storageElementLinks)
        {
            if (usedFurnitureMap.containsKey(storageElementLink))
            {
                FacadeContext.getFurnitureFacade().releaseBy(storageElementLink, usedFurnitureMap.get(storageElementLink));
            }
            else
            {
                FacadeContext.getFurnitureFacade().releaseBy(storageElementLink, 0);
            }
        }
    }

    @Override
    public void saveCuttingModel(LinearCuttingModel cuttingModel)
    {

        List<FurnitureTypeCodePair> pairs = cuttingModel.getPairs();
        for (FurnitureTypeCodePair pair : pairs)
        {
            Strips strips = cuttingModel.getStrips(pair);
            removeUnusedSegments(strips);
            LinearStripsEntity stripsEntity = loadBy(cuttingModel.getOrderGroup(), pair);
            stripsEntity.setFurnitureCode(pair.getFurnitureCode());
            stripsEntity.setFurnitureType(pair.getFurnitureType());
            stripsEntity.setOrderGroup(cuttingModel.getOrderGroup());
            stripsEntity.setRotation(strips.isAllowRotation());
            stripsEntity.setSawWidth(strips.getSawWidth());
            stripsEntity.setStrips(StripsXmlSerializer.getInstance().serialize(strips));
            save(stripsEntity);
        }
    }

    private LinearStripsEntity loadBy(OrderGroup orderGroup, FurnitureTypeCodePair pair)
    {
        SearchFilter searchFilter = SearchFilter.instanceSingle();
        searchFilter.eq(LinearStripsEntity.PROPERTY_ORDER_GROUP, orderGroup);
        searchFilter.eq(LinearStripsEntity.PROPERTY_priceAware, pair.getFurnitureType());
        searchFilter.eq(LinearStripsEntity.PROPERTY_priced, pair.getFurnitureCode());

        return getFirstBy(searchFilter);
    }

    public List<LinearStripsEntity> loadAllBy(OrderGroup orderGroup)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(LinearStripsEntity.PROPERTY_ORDER_GROUP, orderGroup);
        return loadAll(searchFilter);
    }

    @Override
    public void deleteAllBy(OrderGroup orderGroup)
    {
        List<LinearStripsEntity> stripsEntities = loadAllBy(orderGroup);
        for (LinearStripsEntity linearStripsEntity : stripsEntities)
        {
            FacadeContext.getStorageElementLinkFacade().deleteAllBy(linearStripsEntity);
            delete(linearStripsEntity);
        }
    }

    @Override
    public LinearCuttingModel loadLinearCuttingModelBy(OrderGroup orderGroup)
    {
        List<LinearStripsEntity> stripsEntities = loadAllBy(orderGroup);
        if (stripsEntities.isEmpty())
        {
            return null;
        }
        else
        {
            LinearCuttingModel cuttingModel = new LinearCuttingModel();
            cuttingModel.setOrderGroup(orderGroup);

            List<FurnitureTypeCodePair> pairs = new ArrayList<FurnitureTypeCodePair>();

            for (LinearStripsEntity stripsEntity : stripsEntities)
            {
                FurnitureTypeCodePair pair = new FurnitureTypeCodePair(stripsEntity.getFurnitureType(),
                        stripsEntity.getFurnitureCode());
                pairs.add(pair);
                Strips strips = StripsXmlSerializer.getInstance().unserialize(stripsEntity.getStrips());
                if (strips != null)
                {
                    strips.setStripsEntity(stripsEntity);
                    cuttingModel.putStrips(pair, strips);
                }
            }
            cuttingModel.setPairs(pairs);
            return cuttingModel;
        }
    }

}
