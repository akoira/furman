package by.dak.cutting.doors.mech.stringConverter;

import by.dak.cutting.doors.mech.DoorComponent;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 27.08.2009
 * Time: 13:42:03
 * To change this template use File | Settings | File Templates.
 */
public class DoorComponent2StringConverter implements EntityToStringConverter<DoorComponent>
{
    @Override
    public String convert(DoorComponent entity)
    {
        if (entity == null)
        {
            return "";
        }
        return entity.getName();
    }
}
