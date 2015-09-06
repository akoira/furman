package by.dak.cutting.doors.profile.facade;

import by.dak.cutting.doors.profile.ProfileCompDef;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.doors.profile.ProfilePosition;
import by.dak.cutting.doors.profile.dao.ProfileCompDefDao;
import by.dak.cutting.facade.BaseFacadeImpl;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 11.9.2009
 * Time: 18.30.06
 * To change this template use File | Settings | File Templates.
 */
public class ProfileCompDefFacadeImpl extends BaseFacadeImpl<ProfileCompDef> implements ProfileCompDefFacade
{
    @Override
    public ProfileCompDef findBy(ProfileDef profile, ProfilePosition position)
    {
        return ((ProfileCompDefDao) getDao()).findBy(profile, position);

    }
}
