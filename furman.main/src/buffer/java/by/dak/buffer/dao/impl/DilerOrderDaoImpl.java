package by.dak.buffer.dao.impl;

import by.dak.buffer.dao.DilerOrderDao;
import by.dak.buffer.entity.DilerOrder;
import by.dak.persistence.dao.impl.GenericDaoImpl;
import by.dak.persistence.entities.Customer;
import org.hibernate.Query;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.11.2010
 * Time: 10:56:42
 * To change this template use File | Settings | File Templates.
 */
public class DilerOrderDaoImpl extends GenericDaoImpl<DilerOrder> implements DilerOrderDao
{
    @Override
    public List<DilerOrder> getDilerOrderBy(Customer customer)
    {

        Query query = null;
        List<DilerOrder> dilerOrderList = null;

        query = getSession().createSQLQuery("select do.*" +
                " from DILER_ORDER do WHERE do.FK_CUSTOMER_ID = ?").addEntity(DilerOrder.class);
        dilerOrderList = query.setString(0, customer.getId().toString()).list();
        return dilerOrderList;
    }
}
