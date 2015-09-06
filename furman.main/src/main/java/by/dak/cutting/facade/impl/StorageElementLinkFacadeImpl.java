package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.StorageElementLinkFacade;
import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.cutting.entities.AStripsEntity;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;

import javax.persistence.DiscriminatorValue;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 22.02.2010
 * Time: 23:45:58
 * To change this template use File | Settings | File Templates.
 */
public class StorageElementLinkFacadeImpl extends BaseFacadeImpl<StorageElementLink> implements StorageElementLinkFacade
{

    @Override
    public StorageElementLink createAndSaveBy(ABoardStripsEntity stripsEntity, Board board, int count)
    {
        StorageElementLink storageElementLink = new StorageElementLink();
        storageElementLink.setAmount(count);
        storageElementLink.setStripsEntity(stripsEntity);
        storageElementLink.setStoreElement(board);
        save(storageElementLink);
        return storageElementLink;
    }

    @Override
    public StorageElementLink findByTempStatus(LinearStripsEntity stripsEntity, FurnitureType type, FurnitureCode code)
    {
        SearchFilter searchFilter = SearchFilter.instanceSingle();
        searchFilter.eq(StorageElementLink.PROPERTY_stripsEntity, stripsEntity);
        searchFilter.eq(StorageElementLink.PROPERTY_storeElement + "." + AStoreElement.PROPERTY_priceAware, type);
        searchFilter.eq(StorageElementLink.PROPERTY_storeElement + "." + AStoreElement.PROPERTY_priced, code);
        searchFilter.eq(StorageElementLink.PROPERTY_storeElement + "." + AStoreElement.PROPERTY_status, StoreElementStatus.temp);
        List<StorageElementLink> storageElementLinks = loadAll(searchFilter);
        if (storageElementLinks.size() != 0)
        {
            return storageElementLinks.get(0);
        }
        return null;
    }

    @Override
    public StorageElementLink findUniqueStorageElementLink(LinearStripsEntity stripsEntity, Furniture furniture)
    {
        SearchFilter searchFilter = SearchFilter.instanceSingle();
        searchFilter.eq(StorageElementLink.PROPERTY_stripsEntity, stripsEntity);
        searchFilter.eq(StorageElementLink.PROPERTY_storeElement, furniture);
        List<StorageElementLink> storageElementLinks = loadAll(searchFilter);
        if (storageElementLinks.size() > 0)
        {
            return storageElementLinks.get(0);
        }
        return null;
    }

    @Override
    public List<StorageElementLink> loadAllBy(AOrder order)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(StorageElementLink.PROPERTY_stripsEntity + "." + ABoardStripsEntity.PROPERTY_order, order);
        filter.eq(StorageElementLink.PROPERTY_storeElement + "." + AStoreElement.PROPERTY_discriminator, Board.class.getAnnotation(DiscriminatorValue.class).value());
        return loadAll(filter);
    }

    @Override
    public StorageElementLink findBy(Board board)
    {
        return findUniqueByField("board", board);
    }

    @Override
    public List<StorageElementLink> loadAllBy(AStripsEntity stripsEntity, String stroreElementDiscriminator)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(StorageElementLink.PROPERTY_stripsEntity, stripsEntity);
        //filter.eq(StorageElementLink.PROPERTY_storeElement + "." + AStoreElement.PROPERTY_discriminator, stroreElementDiscriminator);
        return loadAll(filter);
    }

    @Override
    public StorageElementLink createAndSaveBy(LinearStripsEntity stripsEntity, Furniture furniture, int count)
    {
        StorageElementLink storageElementLink = new StorageElementLink();
        storageElementLink.setAmount(count);
        storageElementLink.setStripsEntity(stripsEntity);
        storageElementLink.setStoreElement(furniture);
        save(storageElementLink);
        return storageElementLink;
    }


    @Override
    public void deleteAllBy(LinearStripsEntity linearStripsEntity)
    {
        List<StorageElementLink> storageElementLinks = FacadeContext.getStorageElementLinkFacade().
                loadAllBy(linearStripsEntity, Furniture.class.getAnnotation(DiscriminatorValue.class).value());
        for (StorageElementLink storageElementLink : storageElementLinks)
        {
            FacadeContext.getFurnitureFacade().releaseBy(storageElementLink, 0);
        }
    }

    @Override
    public void deleteAllBy(ABoardStripsEntity boardStripsEntity)
    {
        List<StorageElementLink> storageElementLinks = FacadeContext.getStorageElementLinkFacade().
                loadAllBy(boardStripsEntity, Board.class.getAnnotation(DiscriminatorValue.class).value());
        for (StorageElementLink storageElementLink : storageElementLinks)
        {
            FacadeContext.getBoardFacade().releaseBy(storageElementLink, 0);
        }
    }

    @Override
    public void deleteAllBy(AOrder order)
    {
        List<StorageElementLink> storageElementLinks = loadAllBy(order);
        for (StorageElementLink storageElementLink : storageElementLinks)
        {
            FacadeContext.getBoardFacade().releaseBy(storageElementLink, 0);
        }
    }

}
