package by.dak.cutting.agt.facade;

import by.dak.cutting.afacade.facade.AFacadeFacadeImpl;
import by.dak.cutting.agt.AGTFacade;
import by.dak.persistence.entities.FurnitureLink;

/**
 * User: akoyro
 * Date: 01.09.2010
 * Time: 20:37:41
 */
public class AGTFacadeFacadeImpl extends AFacadeFacadeImpl<AGTFacade> implements AGTFacadeFacade
{
    @Override
    protected void fillTransient(AGTFacade facade, FurnitureLink link)
    {
        String propertyName = link.getName();
        fillTransient(facade, propertyName.replaceAll("agt",""), link);
    }

    public static double getProfileWidth(AGTFacade facade)
    {
        double profileWidth = facade.getProfileColor().getWidth();
        if (profileWidth == 0)
        {
            profileWidth = facade.getProfileType().getWidth();
        }
        return profileWidth;
    }
}
