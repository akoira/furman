package by.dak.cutting.doors.profile;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 23.9.2009
 * Time: 17.01.08
 * To change this template use File | Settings | File Templates.
 */
public class TProfileDef
{
    @Test
    public void testProfileDef()
    {
        new SpringConfiguration();
        ProfileDef profileDef = new ProfileDef();
        profileDef.setName("profile");
//        profileDef.setId(42354);
//        profileDef.setDownProfileCompDef(FacadeContext.getProfileCompDefFacade().loadAll().get(0));
//        profileDef.setLeftProfileCompDef(FacadeContext.getProfileCompDefFacade().loadAll().get(0));
//        profileDef.setRightProfileCompDef(FacadeContext.getProfileCompDefFacade().loadAll().get(0));
//        profileDef.setUpProfileCompDef(FacadeContext.getProfileCompDefFacade().loadAll().get(0));
        FacadeContext.getProfileDefFacade().save(profileDef);

        assertNotNull(profileDef.getId());
        assertNotNull(FacadeContext.getProfileDefFacade().findBy(profileDef.getId()));
    }
}
