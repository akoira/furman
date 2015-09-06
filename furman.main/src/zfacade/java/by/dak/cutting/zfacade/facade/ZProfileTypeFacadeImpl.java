package by.dak.cutting.zfacade.facade;

import by.dak.cutting.afacade.facade.AProfileTypeFacadeImpl;
import by.dak.cutting.zfacade.ZFurnitureType;
import by.dak.cutting.zfacade.ZProfileType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureType;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 11:07:40
 */
public class ZProfileTypeFacadeImpl extends AProfileTypeFacadeImpl<ZProfileType> implements ZProfileTypeFacade
{
    public void fillChildTypes(ZProfileType value)
    {
        if (value.hasId())
        {
            if (value.getAngleType() == null)
            {
                value.setAngleType((FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(value, ZFurnitureType.angle.name()));
            }

            if (value.getRubberType() == null)
            {
                value.setRubberType((FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(value, ZFurnitureType.rubber.name()));
            }

            if (value.getButtType() == null)
            {
                value.setButtType((FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(value, ZFurnitureType.butt.name()));
            }
        }
    }


}
