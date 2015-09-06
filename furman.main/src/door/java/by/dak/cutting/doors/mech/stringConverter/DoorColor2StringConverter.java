package by.dak.cutting.doors.mech.stringConverter;

import by.dak.cutting.doors.mech.DoorColor;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 27.08.2009
 * Time: 13:38:42
 * To change this template use File | Settings | File Templates.
 */
public class DoorColor2StringConverter implements EntityToStringConverter<DoorColor>
{
    @Override
    public String convert(DoorColor entity)
    {
        if (entity == null)
        {
            return "";
        }
        return entity.getName();
    }
}
