package by.dak.cutting.facade;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.predefined.MaterialType;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 15.07.2010
 * Time: 19:07:26
 */
public class MaterialTypeHelper
{
    public List<PriceAware> getPriceAwaresBy(MaterialType materialType)
    {
        switch (materialType)
        {
            case board:
                return new ArrayList<PriceAware>(FacadeContext.getBoardDefFacade().findSimpleBoardDefs());
            default:
                return FacadeContext.getFacadeBy(materialType.priceAwareClass()).loadAll();
        }
    }

    public PriceAware newPriceAwareBy(MaterialType materialType)
    {
        return FacadeContext.createNewInstance(materialType.priceAwareClass());
    }

    public BaseFacade getTypeFacadeBy(MaterialType materialType)
    {
        return FacadeContext.getFacadeBy(materialType.priceAwareClass());
    }

    public List<? extends Priced> findAllPriceds(MaterialType type)
    {
        return FacadeContext.getFacadeBy(type.pricedClass()).loadAll();
    }

    public List<? extends Priced> findPricedsBy(PriceAware priceAware)
    {
        MaterialType type = MaterialType.valueOf(priceAware.getClass());
        return ((PricedFacade)FacadeContext.getFacadeBy(type.pricedClass())).findBy(priceAware);
    }

    public Priced newPricedBy(PriceAware value)
    {
        MaterialType type = MaterialType.valueOf(value.getClass());
        try
        {
            return type.pricedClass().getConstructor(null).newInstance(null);
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
