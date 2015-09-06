package by.dak.cutting.doors.profile;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 11.9.2009
 * Time: 22.50.09
 * To change this template use File | Settings | File Templates.
 */
public class TProfileCompDef
{
    @Test
    public void testProfileCompDef()
    {
        new SpringConfiguration();
        ProfileCompDef profileCompDef = new ProfileCompDef();
        profileCompDef.setWidth(12);
        profileCompDef.setCode("code");
        profileCompDef.setIndent(1);
        profileCompDef.setLength((long) 2);
        profileCompDef.setMatThickness(3);
//        profileCompDef.setId(3422);
        profileCompDef.setProfileDef(FacadeContext.getProfileDefFacade().loadAll().get(0));
        FacadeContext.getProfileCompDefFacade().save(profileCompDef);
        assertNotNull(profileCompDef.getId());
        assertNotNull(FacadeContext.getProfileCompDefFacade().findBy(profileCompDef.getId()));
    }
}
