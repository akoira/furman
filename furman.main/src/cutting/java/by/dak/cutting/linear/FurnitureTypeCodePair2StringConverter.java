package by.dak.cutting.linear;

import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 10.03.11
 * Time: 20:28
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTypeCodePair2StringConverter implements EntityToStringConverter<FurnitureTypeCodePair>
{
    @Override
    public String convert(FurnitureTypeCodePair entity)
    {
        return new StringBuffer(StringValueAnnotationProcessor.getProcessor().convert(entity.getFurnitureType())).
                append("/").append(StringValueAnnotationProcessor.getProcessor().convert(entity.getFurnitureCode())).toString();

    }
}
