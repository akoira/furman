package by.dak.cutting.doors.profile.facade;

import by.dak.cutting.doors.profile.ProfileCompDef;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.doors.profile.ProfilePosition;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProfileCompDefFacade extends BaseFacade<ProfileCompDef>
{
    public ProfileCompDef findBy(ProfileDef profile, ProfilePosition position);
}
