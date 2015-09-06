package by.dak.cutting.swing.dictionaries.multiselect;

import by.dak.persistence.entities.PriceEntity;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 07.06.2009
 * Time: 20:22:14
 * To change this template use File | Settings | File Templates.
 */
public class Priced2StringConverter implements EntityToStringConverter<PriceEntity>
{
    @Override
    public String convert(PriceEntity entity)
    {
        return StringValueAnnotationProcessor.getProcessor().convert(entity.getPriced());
    }
}