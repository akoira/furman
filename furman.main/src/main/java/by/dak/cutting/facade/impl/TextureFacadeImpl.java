package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.TextureFacade;
import by.dak.persistence.FinderException;
import by.dak.persistence.dao.TextureDao;
import by.dak.persistence.entities.*;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class TextureFacadeImpl extends BaseFacadeImpl<TextureEntity> implements TextureFacade
{


    @Override
    public TextureEntity findTextureByCode(String code)
    {
        return dao.findUniqueByField(TextureEntity.PROPERTY_code, code);
    }

    @Override
    public List<TextureEntity> findTexturesByGroup(String groupIdentifier) throws FinderException
    {
        return dao.findAllByField(TextureEntity.PROPERTY_groupIdentifier, groupIdentifier);
    }

    private List<TextureEntity> findTexturesBy(PriceAware priceAware)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("prices.priceAware", priceAware);
        searchFilter.addAscOrder("name");
        return loadAll(searchFilter);
    }


    @Override
    public List<TextureEntity> findTexturesBy(BoardDef boardDef)
    {
        return findTexturesBy((PriceAware) boardDef);
    }

    @Override
    public TextureEntity getTextureBy(BoardDef boardDef, String code)
    {
        SearchFilter searchFilter = SearchFilter.instanceSingle();
        searchFilter.eq("prices.priceAware", boardDef);
        searchFilter.eq("code", code);
        searchFilter.addAscOrder("name");

        return getFirstBy(searchFilter);
    }

    @Override
    public List<TextureEntity> findTexturesBy(BoardDef boardDef, Manufacturer manufacturer)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq("prices.priceAware", boardDef);
        searchFilter.eq("manufacturer", manufacturer);
        searchFilter.addAscOrder("name");
        return loadAll(searchFilter);
    }

    @Override
    public List<TextureEntity> findTexturesBy(BorderDefEntity borderDef)
    {
        return findTexturesBy((PriceAware) borderDef);
    }

    @Override
    public TextureEntity findBy(BorderDefEntity gluingBorderDef, TextureEntity detailTexture)
    {
        return ((TextureDao) dao).findTextureBy(gluingBorderDef, detailTexture);
    }

    @Override
    public List<TextureEntity> findBy(PriceAware priceAware)
    {
        //todo Сделать общий метод а не switch
        if (priceAware instanceof BorderDefEntity)
        {
            return findTexturesBy((BorderDefEntity) priceAware);
        }
        else if (priceAware instanceof BoardDef)
        {
            return findTexturesBy((BoardDef) priceAware);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean isUniqueByCodeSurfaceManf(TextureEntity texture)
    {
        return ((TextureDao) dao).isUniqueByCodeSurfaceManf(texture);
    }

    @Override
    public List<TextureEntity> loadAll(SearchFilter filter)
    {
        filter.addColumnOrder(new SearchFilter.DCriterion<Order>("name", Order.asc("name")));
        return super.loadAll(filter);
    }
}
