package by.dak.report.jasper.common.dao;

import by.dak.persistence.dao.impl.GenericDaoImpl;
import by.dak.persistence.entities.AOrder;
import by.dak.report.jasper.common.data.CommonData;
import org.hibernate.Query;

/**
 * User: akoyro
 * Date: 27.11.2010
 * Time: 0:36:34
 */
public class CommonDataDaoImpl extends GenericDaoImpl<CommonData> implements CommonDataDao
{
    public void deleteAllBy(AOrder order)
    {
        Query query = getSession().getNamedQuery("deleteAllByOrder");
        query.setEntity("order", order);
        query.executeUpdate();
    }
}
