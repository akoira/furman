package by.dak.cutting.doors.profile;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 17.9.2009
 * Time: 15.55.06
 * To change this template use File | Settings | File Templates.
 */
public class TProfileComp
{
    @Test
    public void testProfileComp()
    {
        new SpringConfiguration();
        ProfileComp profileComp = new ProfileComp();

        profileComp.setProfileComponentDef(FacadeContext.getProfileCompDefFacade().loadAll().get(0));
        profileComp.setLength((long) 12);
        FacadeContext.getProfileCompFacade().save(profileComp);
        assertNotNull(profileComp.getId());
        assertNotNull(FacadeContext.getProfileCompFacade().findBy(profileComp.getId()));
    }
}
