package by.dak.persistence.convert;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

/**
 * @author akoyro
 * @version 0.1 30.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class TextureBoardDefPair2StringConverter implements EntityToStringConverter<TextureBoardDefPair>
{
    @Override
    public String convert(TextureBoardDefPair entity)
    {

        return new StringBuffer(StringValueAnnotationProcessor.getProcessor().convert(entity.getTexture())).
                append(" / ").append(StringValueAnnotationProcessor.getProcessor().convert(entity.getBoardDef())).toString();
    }
}
