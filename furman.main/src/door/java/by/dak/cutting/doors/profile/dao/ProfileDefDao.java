package by.dak.cutting.doors.profile.dao;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.persistence.dao.GenericDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileDefDao extends GenericDao<ProfileDef>
{
    public List<ProfileDef> findProfileDefinitionsWithFilter(final SearchFilter filter);
}
