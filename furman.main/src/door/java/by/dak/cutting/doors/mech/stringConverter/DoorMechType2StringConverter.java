package by.dak.cutting.doors.mech.stringConverter;

import by.dak.cutting.doors.mech.DoorMechType;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 27.08.2009
 * Time: 13:42:49
 * To change this template use File | Settings | File Templates.
 */
public class DoorMechType2StringConverter implements EntityToStringConverter<DoorMechType>
{
    @Override
    public String convert(DoorMechType entity)
    {
        if (entity == null)
        {
            return "";
        }
        return entity.getName();
    }
}
