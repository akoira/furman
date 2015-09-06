package by.dak.cutting.doors.profile.dao;

import by.dak.cutting.doors.profile.ProfileCompDef;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.doors.profile.ProfilePosition;
import by.dak.persistence.dao.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileCompDefDao extends GenericDao<ProfileCompDef>
{
    public ProfileCompDef findBy(ProfileDef profileDef, ProfilePosition position);
}
