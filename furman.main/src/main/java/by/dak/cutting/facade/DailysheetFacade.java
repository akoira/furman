package by.dak.cutting.facade;

import by.dak.persistence.entities.Dailysheet;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DailysheetFacade extends BaseFacade<Dailysheet>
{

    Dailysheet loadCurrentDailysheet();

    void save(Dailysheet dailysheet);

}
