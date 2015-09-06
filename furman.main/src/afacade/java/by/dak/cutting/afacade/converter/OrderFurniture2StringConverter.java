package by.dak.cutting.afacade.converter;

import by.dak.persistence.entities.OrderFurniture;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * @author akoyro
 * @version 0.1 30.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class OrderFurniture2StringConverter implements EntityToStringConverter<OrderFurniture>
{
    @Override
    public String convert(OrderFurniture entity)
    {
        return (entity.getBoardDef() != null ? entity.getBoardDef().getName() : "") +
                " / " + (entity.getTexture() != null ? entity.getTexture().getName() : "");
    }
}