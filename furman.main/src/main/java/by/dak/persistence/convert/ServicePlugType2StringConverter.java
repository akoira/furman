package by.dak.persistence.convert;

import by.dak.persistence.entities.ServicePlugType;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.utils.convert.EntityToStringConverter;

public class ServicePlugType2StringConverter implements EntityToStringConverter<ServicePlugType> {
    @Override
    public String convert(ServicePlugType entity) {
        return entity.getName();
    }
}

