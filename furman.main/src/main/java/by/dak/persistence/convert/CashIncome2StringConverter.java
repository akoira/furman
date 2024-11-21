package by.dak.persistence.convert;

import by.dak.persistence.entities.CashIncome;
import by.dak.persistence.entities.Order;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

public class CashIncome2StringConverter implements EntityToStringConverter<CashIncome> {
    @Override
    public String convert(CashIncome entity) {
        return entity.getCustomer().getName() + " " + entity.getAmount();
    }
}