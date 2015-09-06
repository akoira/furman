package by.dak.cutting.afacade.dao;

import by.dak.cutting.afacade.ALink;
import by.dak.persistence.dao.impl.GenericDaoImpl;
import org.hibernate.Query;

/**
 * User: akoyro
 * Date: 05.08.2010
 * Time: 17:00:01
 */
public abstract class ALinkDaoImpl<L extends ALink, E> extends GenericDaoImpl<L> implements ALinkDao<L, E>
{
    protected abstract Query getDeleteByParentQuery();

    public void deleteBy(E parent)
    {
        Query q = getDeleteByParentQuery();
        q.setEntity("parent", parent);
        q.executeUpdate();
    }
}
