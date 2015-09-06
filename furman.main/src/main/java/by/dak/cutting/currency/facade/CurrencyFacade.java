package by.dak.cutting.currency.facade;

import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.cutting.facade.BaseFacade;
import by.dak.persistence.entities.Dailysheet;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CurrencyFacade extends BaseFacade<Currency>
{
    Currency findCurrentBy(CurrencyType currencyType, Dailysheet dailysheet);

    Currency findCurrentBy(CurrencyType currencyType, Dailysheet dailysheet, boolean loadFromBase);
}
