package by.dak.persistence.convert;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing.Gluieng;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 18.8.2009
 * Time: 16.02.08
 * To change this template use File | Settings | File Templates.
 */
public class Gluieng2StringConverter implements EntityToStringConverter<Gluieng>
{
    @Override
    public String convert(Gluieng entity)
    {
        return new StringBuffer(entity.getTexture().getName()).append(' ').append(entity.getBorderDef().getName()).toString();
    }
}
