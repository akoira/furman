package by.dak.cutting.doors.profile.facade;

import by.dak.cutting.doors.profile.ProfileComp;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProfileCompFacade extends BaseFacade<ProfileComp>
{
}
