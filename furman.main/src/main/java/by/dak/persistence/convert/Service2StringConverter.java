package by.dak.persistence.convert;

import by.dak.persistence.entities.Service;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

/**
 * User: akoyro
 * Date: 02.12.2009
 * Time: 0:06:43
 */
public class Service2StringConverter implements EntityToStringConverter<Service>
{
    @Override
    public String convert(Service entity)
    {
        return StringValueAnnotationProcessor.getProcessor().convert(entity.getServiceType());
    }
}
