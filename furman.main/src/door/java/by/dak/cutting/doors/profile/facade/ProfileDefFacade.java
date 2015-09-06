package by.dak.cutting.doors.profile.facade;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.doors.profile.ProfileDef;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProfileDefFacade extends BaseFacade<ProfileDef>
{
    /**
     * Возвращает  ProfileDef по имени
     *
     * @param name
     * @return
     */
    public ProfileDef getProfileDefByName(String name);

    /**
     * Возвращает list по фильтру
     *
     * @param filter
     * @return
     */
    public List<ProfileDef> findProfileDefinitionsWithFilter(final SearchFilter filter);
}
