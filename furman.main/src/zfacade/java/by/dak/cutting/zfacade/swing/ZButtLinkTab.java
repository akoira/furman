package by.dak.cutting.zfacade.swing;

import by.dak.cutting.afacade.swing.AFurnitureLinkTab;
import by.dak.cutting.zfacade.ZButtLink;
import by.dak.persistence.entities.FurnitureLink;

/**
 * User: akoyro
 * Date: 31.07.2010
 * Time: 22:29:20
 */
public class ZButtLinkTab extends AFurnitureLinkTab<ZButtLink>
{
    public static final String[] VisibleProperties = new String[]{
            FurnitureLink.PROPERTY_furnitureType,
            FurnitureLink.PROPERTY_furnitureCode,
            FurnitureLink.PROPERTY_amount,
            ZButtLink.PROPERTY_side
    };

    @Override
    public String[] getVisibleProperties()
    {
        return VisibleProperties;
    }
}
