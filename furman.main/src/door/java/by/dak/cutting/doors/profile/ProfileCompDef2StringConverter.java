package by.dak.cutting.doors.profile;

import by.dak.utils.convert.EntityToStringConverter;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 11.9.2009
 * Time: 17.39.41
 * To change this template use File | Settings | File Templates.
 */
public class ProfileCompDef2StringConverter implements EntityToStringConverter<ProfileCompDef>
{
    @Override
    public String convert(ProfileCompDef entity)
    {
        if (entity == null)
        {
            return "";
        }
        else
        {
            return entity.getCode();
        }
    }
}
