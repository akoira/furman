package by.dak.persistence.dbscripts.update.t9290;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Order;
import by.dak.report.jasper.common.data.CommonDataCreator;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateInterceptor;

import java.math.BigInteger;
import java.util.List;

/**
 * User: akoyro
 * Date: 15.09.11
 * Time: 23:42
 */
public class RemoveDublicateCommonData
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        HibernateInterceptor hibernateInterceptor = (HibernateInterceptor) FacadeContext.getApplicationContext().getBean("hibernateInterceptor");
        Session session = hibernateInterceptor.getSessionFactory().openSession();

        String sql = "select distinct fo.id from common_data cd " +
                "inner join furn_order fo on fo.id = cd.order_id " +
                "inner join daily_sheet ds on fo.FK_CREATED_DAILY_SHEET_ID = ds.id " +
                "inner join (select order_id, ctype, `name`, count(id) as count from common_data " +
                "group by order_id, ctype, `name`) dcd on cd.order_id = dcd.order_id " +
                "where " +
                "dcd.count > 1 " +
                "order by cd.order_id desc";


        SQLQuery sqlQuery = session.createSQLQuery(sql);
        List list = sqlQuery.list();
        session.close();
        for (Object o : list)
        {
            try
            {
                Order order = FacadeContext.getOrderFacade().findBy(((BigInteger) o).longValue());
                FacadeContext.getCommonDataFacade().deleteAll(order);
                CuttingModel cuttingModel = FacadeContext.getStripsFacade().loadCuttingModel(order).load();
                CommonDataCreator commonDataCreator = new CommonDataCreator(cuttingModel);
                commonDataCreator.create();
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }


    }
}
