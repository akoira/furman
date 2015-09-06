package by.dak.cutting.doors.profile.dao;

import by.dak.cutting.doors.profile.ProfileCompDef;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.doors.profile.ProfilePosition;
import by.dak.persistence.dao.impl.GenericDaoImpl;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 11.9.2009
 * Time: 18.04.19
 * To change this template use File | Settings | File Templates.
 */
public class ProfileCompDefDaoImpl extends GenericDaoImpl<ProfileCompDef> implements ProfileCompDefDao
{

    @Override
    public ProfileCompDef findBy(ProfileDef profileDef, ProfilePosition position)
    {
        List<ProfileCompDef> compDefs = findByCriteria(Restrictions.and(eq("profileDef", profileDef), eq("position", position)));
        if (!compDefs.isEmpty())
        {
            return compDefs.get(0);
        }
        else
        {
            return null;
        }
    }
}
