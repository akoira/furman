package by.dak.persistence.convert;

import by.dak.persistence.entities.MelamineGluing;
import by.dak.utils.convert.EntityToStringConverter;

public class MelamineGluing2StringConverter implements EntityToStringConverter<MelamineGluing> {
    @Override
    public String convert(MelamineGluing entity) {
        return entity.getName();
    }
}

