package by.dak.cutting.agt.facade;

import by.dak.cutting.afacade.facade.AProfileColorFacadeImpl;
import by.dak.cutting.agt.AGTColor;
import by.dak.cutting.agt.AGTFurnitureType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureCode;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 11:07:40
 */
public class AGTColorFacadeImpl extends AProfileColorFacadeImpl<AGTColor> implements AGTColorFacade
{
    public void fillChildTypes(AGTColor value)
    {
        if (value.hasId())
        {
            if (value.getGlueCode() == null)
            {
                value.setGlueCode((FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(value, AGTFurnitureType.agtglue.name()));
            }

            if (value.getRubberCode() == null)
            {
                value.setRubberCode((FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(value, AGTFurnitureType.agtrubber.name()));
            }

            if (value.getDowelCode() == null)
            {
                value.setDowelCode((FurnitureCode) FacadeContext.getFurnitureCodeLinkFacade().findBy(value, AGTFurnitureType.agtdowel.name()));
            }

        }
    }

}