package by.dak.cutting.afacade.facade;

import by.dak.cutting.afacade.AProfileColor;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.impl.FurnitureCodeFacadeImpl;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.types.FurnitureCode;

import java.util.List;
import java.util.Map;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 11:07:40
 */
public abstract class AProfileColorFacadeImpl<E extends AProfileColor> extends BaseFacadeImpl<E> implements AProfileColorFacade<E>
{
    @Override
    public List<E> findBy(PriceAware priceAware)
    {
        return loadAll(FurnitureCodeFacadeImpl.getFilterPriceAware(priceAware));
    }

    @Override
    public void save(E entity)
    {
        super.save(entity);
        recreateLinks(entity);
    }

    private void recreateLinks(E entity)
    {
        FacadeContext.getFurnitureCodeLinkFacade().deleteBy(entity);
        Map<String, FurnitureCode> codes = entity.getTransients();
        for (String keyword : codes.keySet())
        {
            FacadeContext.getFurnitureCodeLinkFacade().createAndSaveBy(entity, codes.get(keyword), keyword);
        }
    }


}