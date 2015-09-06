package by.dak.cutting.doors.profile.dao;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.persistence.dao.impl.GenericDaoImpl;
import org.hibernate.Criteria;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 21.9.2009
 * Time: 11.02.32
 * To change this template use File | Settings | File Templates.
 */
public class ProfileDefDaoImpl extends GenericDaoImpl<ProfileDef> implements ProfileDefDao
{
    public List<ProfileDef> findProfileDefinitionsWithFilter(final SearchFilter filter)
    {     //для загрузки list'a для справочника
        Criteria execCriteria = getSession().createCriteria(ProfileDef.class);
        execCriteria.setFirstResult(filter.getStartIndex());
        execCriteria.setMaxResults(filter.getPageSize());
        return execCriteria.list();
    }
}
