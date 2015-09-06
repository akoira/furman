package by.dak.cutting.doors.mech.stringConverter;

import by.dak.cutting.doors.mech.DoorMechDef;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 27.08.2009
 * Time: 13:42:19
 * To change this template use File | Settings | File Templates.
 */
public class DoorMechDef2StringConverter implements EntityToStringConverter<DoorMechDef>
{
    @Override
    public String convert(DoorMechDef entity)
    {
        if (entity == null)
        {
            return "";
        }
        return entity.getName();
    }
}
