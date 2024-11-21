package by.dak.cutting.currency.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.cutting.currency.persistence.entity.CurrencyType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Dailysheet;
import by.dak.utils.convert.TimeUtils;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TCurrency {

    @Test
    public void getCurrencyByDateTest() throws ParseException {
        new SpringConfiguration();

        String inputString = "26-11-2020";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date inputDate = dateFormat.parse(inputString);

        Currency currency = FacadeContext.getCurrencyFacade().findCurrentBy(CurrencyType.USD, inputDate);

        System.out.println(currency);
    }
}
