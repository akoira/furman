package by.dak.cutting.swing.dictionaries;

import by.dak.persistence.dao.impl.GenericDaoImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 07.06.2009
 * Time: 17:41:52
 * To change this template use File | Settings | File Templates.
 */
public class BDaoImpl extends GenericDaoImpl<B> implements BDao
{
    @Override
    public B findBy(A a)
    {
        Criteria criteria = getSession().createCriteria(B.class);
        criteria.add(Restrictions.eq("a", a));
        return (B) criteria.uniqueResult();
    }
}
