package by.dak.cutting.afacade.facade;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.afacade.ALink;
import by.dak.cutting.afacade.FurnitureTypeLink;
import by.dak.cutting.afacade.dao.ALinkDao;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.utils.GenericUtils;

import java.util.List;

/**
 * User: akoyro
 * Date: 05.08.2010
 * Time: 16:59:37
 */
public abstract class ALinkFacadeImpl<L extends ALink, E> extends BaseFacadeImpl<L> implements ALinkFacade<L,E>
{
    @Override
    public void deleteBy(E parent)
    {
        ((ALinkDao)getDao()).deleteBy(parent);
    }


    @Override
    public List<L> findAllBy(String keyword)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(FurnitureTypeLink.PROPERTY_keyword, keyword);
        filter.addAscOrder(FurnitureTypeLink.PROPERTY_child + "." + PriceAware.PROPERTY_name);
        return loadAll(filter);
    }

    @Override
    public E findBy(E parent, String keyword)
    {
        SearchFilter filter = SearchFilter.instanceSingle();
        filter.eq(FurnitureTypeLink.PROPERTY_parent, parent);
        filter.eq(FurnitureTypeLink.PROPERTY_keyword, keyword);
        List<L> list = loadAll(filter);
        if (list.size() > 0)
        {
            return (E)list.get(0).getChild();
        }
        return null;
    }

    @Override
    public L createAndSaveBy(E parent, E child, String keyword)
    {
        if (child == null)
        {
            return null;
        }
        L link = (L) FacadeContext.createNewInstance(GenericUtils.getParameterClass(this.getClass(),0));
        link.setParent(parent);
        link.setChild(child);
        link.setKeyword(keyword);
        save(link);
        return link;
    }
}
