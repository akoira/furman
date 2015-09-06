package by.dak.persistence.convert;

import by.dak.persistence.entities.Dailysheet;
import by.dak.utils.convert.EntityToStringConverter;

import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 13.02.2009
 * Time: 23:59:24
 * To change this template use File | Settings | File Templates.
 */
public class DailySheet2StringConverter implements EntityToStringConverter<Dailysheet>
{
    @Override
    public String convert(Dailysheet entity)
    {
//        return new StringBuffer(new SimpleDateFormat("dd.MM.yyyy").format(entity.getDate()))
//                .append(" ").append(new Employee2StringConverter().convert(entity.getEmployee())).toString();
        return new SimpleDateFormat("dd.MM.yyyy").format(entity.getDate());
    }
}
