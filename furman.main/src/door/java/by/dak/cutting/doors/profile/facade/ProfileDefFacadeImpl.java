package by.dak.cutting.doors.profile.facade;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.doors.profile.dao.ProfileDefDao;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.persistence.FacadeContext;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 21.9.2009
 * Time: 11.06.23
 * To change this template use File | Settings | File Templates.
 */
public class ProfileDefFacadeImpl extends BaseFacadeImpl<ProfileDef> implements ProfileDefFacade
{

    @Override
    public ProfileDef getProfileDefByName(String name)
    {
        if (FacadeContext.getProfileDefFacade().findUniqueByField("name", name) != null)
        {
            return FacadeContext.getProfileDefFacade().findUniqueByField("name", name);
        }
        else
        {
            return null;
        }

    }

    @Override
    public List<ProfileDef> findProfileDefinitionsWithFilter(SearchFilter filter)
    {   //поиск по фильтру для справочника
        return ((ProfileDefDao) dao).findProfileDefinitionsWithFilter(filter);
    }

}
