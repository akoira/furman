package by.dak.cutting.afacade.dao;

import by.dak.cutting.afacade.FurnitureCodeLink;
import by.dak.persistence.entities.Priced;
import org.hibernate.Query;

/**
 * User: akoyro
 * Date: 05.08.2010
 * Time: 17:00:11
 */
public class FurnitureCodeLinkDaoImpl extends ALinkDaoImpl<FurnitureCodeLink, Priced> implements FurnitureCodeLinkDao
{
    @Override
    protected Query getDeleteByParentQuery()
    {
        return getSession().getNamedQuery("deleteCodeLinksByParent");
    }
}
