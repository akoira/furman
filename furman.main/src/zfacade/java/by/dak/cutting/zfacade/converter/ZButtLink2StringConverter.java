package by.dak.cutting.zfacade.converter;

import by.dak.cutting.zfacade.ZButtLink;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

/**
 * User: akoyro
 * Date: 10.08.2010
 * Time: 14:55:58
 */
public class ZButtLink2StringConverter implements EntityToStringConverter<ZButtLink>
{
    @Override
    public String convert(ZButtLink entity)
    {
        return new StringBuffer(entity.getFurnitureType()!= null ? entity.getFurnitureType().getName():"")
                .append(" / ")
                .append(entity.getFurnitureCode() != null ? entity.getFurnitureCode().getName():"")
                .append(" - ")
                .append(StringValueAnnotationProcessor.getProcessor().convert(entity.getSide())).toString();

    }
}
