package by.dak.cutting.afacade.dao;

import by.dak.cutting.afacade.FurnitureTypeLink;
import by.dak.persistence.entities.PriceAware;
import org.hibernate.Query;

/**
 * User: akoyro
 * Date: 05.08.2010
 * Time: 17:00:11
 */
public class FurnitureTypeLinkDaoImpl extends ALinkDaoImpl<FurnitureTypeLink, PriceAware> implements FurnitureTypeLinkDao
{
    @Override
    protected Query getDeleteByParentQuery()
    {
        return getSession().getNamedQuery("deleteTypeLinksByParent");

    }
}
