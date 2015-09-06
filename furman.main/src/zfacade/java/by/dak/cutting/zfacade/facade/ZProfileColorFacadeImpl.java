package by.dak.cutting.zfacade.facade;

import by.dak.cutting.afacade.facade.AProfileColorFacadeImpl;
import by.dak.cutting.zfacade.ZFurnitureType;
import by.dak.cutting.zfacade.ZProfileColor;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureCode;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 11:07:40
 */
public class ZProfileColorFacadeImpl extends AProfileColorFacadeImpl<ZProfileColor> implements ZProfileColorFacade
{
    public void fillChildTypes(ZProfileColor value)
    {
        if (value.hasId())
        {
            if (value.getAngleCode() == null)
            {
                value.setAngleCode((FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(value, ZFurnitureType.angle.name()));
            }

            if (value.getRubberCode() == null)
            {
                value.setRubberCode((FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(value, ZFurnitureType.rubber.name()));
            }
        }
    }

}