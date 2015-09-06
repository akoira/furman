package by.dak.persistence.convert;

import by.dak.persistence.entities.Board;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 12.02.2010
 * Time: 10:06:04
 * To change this template use File | Settings | File Templates.
 */
public class Board2StringConverter implements EntityToStringConverter<Board>
{
    @Override
    public String convert(Board entity)
    {
        StringBuffer stringBuffer = new StringBuffer(entity.getPriceAware().getName());
        stringBuffer.append("/").append(entity.getTexture().getName());
        if (entity.getProvider() != null)
        {
            stringBuffer.append("-").append(entity.getProvider().getName());
        }
        stringBuffer.append(" ").append(entity.getLength()).append("x").append(entity.getWidth());
        stringBuffer.append(" ").append(entity.getAmount());
        return stringBuffer.toString();
    }
}
