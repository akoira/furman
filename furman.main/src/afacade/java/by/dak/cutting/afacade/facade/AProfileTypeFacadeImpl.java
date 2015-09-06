package by.dak.cutting.afacade.facade;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.afacade.AProfileType;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureType;

import java.util.List;
import java.util.Map;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 11:07:40
 */
public abstract class AProfileTypeFacadeImpl<E extends AProfileType> extends BaseFacadeImpl<E> implements AProfileTypeFacade<E>
{
    @Override
    public List<E> loadAll()
    {
        return loadAll(SearchFilter.instanceUnbound());
    }

    @Override
    public List<E> loadAll(SearchFilter filter)
    {
        filter.addAscOrder(FurnitureType.PROPERTY_name);
        return super.loadAll(filter);
    }

    @Override
    public void save(E entity)
    {
        super.save(entity);
        FacadeContext.getFurnitureTypeLinkFacade().deleteBy(entity);

        Map<String, FurnitureType> types = entity.getTransients();
        for (String keyword : types.keySet())
        {
            FacadeContext.getFurnitureTypeLinkFacade().createAndSaveBy(entity, types.get(keyword), keyword);
        }
    }
}
