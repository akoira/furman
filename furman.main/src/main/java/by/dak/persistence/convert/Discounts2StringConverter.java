package by.dak.persistence.convert;

import by.dak.persistence.entities.Discounts;
import by.dak.utils.convert.EntityToStringConverter;

public class Discounts2StringConverter implements EntityToStringConverter<Discounts> {
    @Override
    public String convert(Discounts entity) {
        return entity.getType() + " " + entity.getAmount();
    }
}