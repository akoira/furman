package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.DailysheetDao;
import by.dak.persistence.entities.Dailysheet;
import by.dak.utils.convert.TimeUtils;
import org.hibernate.Query;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * @author Denis Koyro
 * @version 0.1 09.12.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class DailysheetDaoImpl extends GenericDaoImpl<Dailysheet> implements DailysheetDao
{

    // TODO findEntitiesByField

    public List<Dailysheet> findBy(java.util.Date date)
    {
        Query query = getSession().createQuery("from Dailysheet d where d.date  = ?");
        query.setTimestamp(0, TimeUtils.getDayTimestamp(date));
        // findAllByField("date", TimeUtils.getDayTimestamp(date));
        return query.list();
    }

    public Dailysheet loadCurrentDailysheet()
    {
        List<Dailysheet> dailysheets = findBy(TimeUtils.getDayTimestamp(new Date(Calendar
                .getInstance().getTimeInMillis())));
        if (dailysheets != null && dailysheets.size() > 0)
        {
            return dailysheets.get(0);
        }
        else
        {
            return null;
        }

    }
}
