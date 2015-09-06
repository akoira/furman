package by.dak.persistence.convert;

import by.dak.cutting.swing.order.tree.DateOrderNode;
import by.dak.utils.convert.EntityToStringConverter;

import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 22.02.2010
 * Time: 10:59:15
 * To change this template use File | Settings | File Templates.
 */
public class OrderDate2StringConverter implements EntityToStringConverter<DateOrderNode>
{
    @Override
    public String convert(DateOrderNode entity)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM");
        return dateFormat.format(entity.getDate());
    }
}
