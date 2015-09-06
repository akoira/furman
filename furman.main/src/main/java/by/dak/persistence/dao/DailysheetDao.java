package by.dak.persistence.dao;

import by.dak.persistence.entities.Dailysheet;
import org.springframework.stereotype.Repository;

/**
 * @author Denis Koyro
 * @version 0.1 07.12.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
@Repository
public interface DailysheetDao extends GenericDao<Dailysheet>
{
    Dailysheet loadCurrentDailysheet();
}
