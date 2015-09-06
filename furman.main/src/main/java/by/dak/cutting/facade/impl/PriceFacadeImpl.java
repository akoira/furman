package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.PriceFacade;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.dao.PriceDao;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.ServiceType;

import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class PriceFacadeImpl extends BaseFacadeImpl<PriceEntity> implements PriceFacade
{

    @Override
    public List<PriceEntity> findAllBy(Service service)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(PriceEntity.PROPERTY_priced, service);
        searchFilter.addAscOrder("priceAware.name");
        return loadAll(searchFilter);
    }

    @Override
    public PriceEntity findUniqueBy(PriceAware priceAware, Priced priced)
    {
        return ((PriceDao) dao).findUniqueBy(priceAware, priced);
    }


    private List<PriceEntity> findBy(PriceAware priceAware, String pricedType)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(PriceEntity.PROPERTY_priceAware, priceAware);
        searchFilter.eq("priced.pricedType", pricedType);
        searchFilter.addAscOrder("priced.name");
        return loadAll(searchFilter);
    }

    @Override
    public List<PriceEntity> findAllBy(PriceAware priceAware)
    {
        String pricedType;
        MaterialType type = MaterialType.valueOf(priceAware.getClass());
        return findBy(priceAware, type.pricedClass().getSimpleName());
    }

    @Override
    public PriceEntity getPrice(PriceAware priceAware, ServiceType serviceType)
    {
        Service service = FacadeContext.getServiceFacade().findUniqueByField("serviceType", serviceType);
        PriceEntity price = null;
        if (service != null)
        {
            price = findUniqueBy(priceAware, service);
        }
        return price;
    }

    @Override
    public PriceEntity getPrice(BoardDef boardDef, ServiceType serviceType)
    {
        Service service = FacadeContext.getServiceFacade().findUniqueByField("serviceType", serviceType);
        PriceEntity price = null;
        if (service != null)
        {
            price = findUniqueBy(boardDef, service);
        }
        return price;
    }

    @Override
    public PriceEntity getPrice(BorderDefEntity borderDefEntity, ServiceType serviceType)
    {
        Service service = FacadeContext.getServiceFacade().findUniqueByField("serviceType", serviceType);
        PriceEntity price = null;
        if (service != null)
        {
            price = findUniqueBy(borderDefEntity, service);
        }
        return price;
    }

    @Override
    public PriceEntity getPrice(TextureBoardDefPair pair)
    {
        return findUniqueBy(pair.getBoardDef(), pair.getTexture());
    }
}
