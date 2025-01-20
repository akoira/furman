package by.dak.cutting.facade;

import by.dak.persistence.entities.Dailysheet;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Transactional
public interface DailysheetFacade extends BaseFacade<Dailysheet>
{

    Dailysheet loadCurrentDailysheet();

    void save(Dailysheet dailysheet);

    List<Dailysheet> findAllByDates(Set<Date> dates);
}
