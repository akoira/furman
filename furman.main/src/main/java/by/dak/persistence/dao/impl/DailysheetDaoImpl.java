package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.DailysheetDao;
import by.dak.persistence.entities.Dailysheet;
import by.dak.utils.convert.TimeUtils;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Denis Koyro
 * @version 0.1 09.12.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class DailysheetDaoImpl extends GenericDaoImpl<Dailysheet> implements DailysheetDao
{

    public List<Dailysheet> findBy(java.util.Date date)
    {
        return findAllByField("date", TimeUtils.getDayTimestamp(date));
    }

    public Dailysheet loadCurrentDailysheet()
    {
        List<Dailysheet> dailysheets = findBy(new Date());
        dailysheets.sort(Comparator.comparing(Dailysheet::getDate));
        if (dailysheets != null && dailysheets.size() > 0)
        {
            return dailysheets.get(dailysheets.size() - 1);
        }
        else
        {
            return null;
        }

    }
}
