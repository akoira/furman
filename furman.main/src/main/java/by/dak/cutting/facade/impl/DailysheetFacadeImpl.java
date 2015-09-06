package by.dak.cutting.facade.impl;

import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.DailysheetFacade;
import by.dak.persistence.dao.DailysheetDao;
import by.dak.persistence.entities.Dailysheet;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class DailysheetFacadeImpl extends BaseFacadeImpl<Dailysheet> implements DailysheetFacade
{
    private DailysheetDao getDailysheetDao()
    {
        return (DailysheetDao) dao;
    }

    public Dailysheet loadCurrentDailysheet()
    {
        Dailysheet dailysheet = getDailysheetDao().loadCurrentDailysheet();
        return dailysheet;
    }

    @Override
    public void save(Dailysheet dailysheet)
    {
        dao.save(dailysheet);
    }
}
