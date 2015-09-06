package by.dak.cutting.agt.facade;

import by.dak.cutting.afacade.facade.AProfileTypeFacadeImpl;
import by.dak.cutting.agt.AGTFurnitureType;
import by.dak.cutting.agt.AGTType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureType;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 11:07:40
 */
public class AGTTypeFacadeImpl extends AProfileTypeFacadeImpl<AGTType> implements AGTTypeFacade
{
    public void fillChildTypes(AGTType value)
    {
        if (value.hasId())
        {
            if (value.getDowelType() == null)
            {
                value.setDowelType((FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(value, AGTFurnitureType.agtdowel.name()));
            }

            if (value.getGlueType() == null)
            {
                value.setGlueType((FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(value, AGTFurnitureType.agtglue.name()));
            }

            if (value.getRubberType() == null)
            {
                value.setRubberType((FurnitureType) FacadeContext.getFurnitureTypeLinkFacade().findBy(value, AGTFurnitureType.agtrubber.name()));
            }

        }
    }

}
