package by.dak.cutting.agt.validate;

import by.dak.cutting.agt.AGTColor;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.validator.FurnitureCodeValidator;

/**
 * User: akoyro
 * Date: 03.11.2010
 * Time: 11:51:10
 */
public class AGTColorValidator extends FurnitureCodeValidator<AGTColor>
{
    @Override
    protected boolean isUnique(AGTColor value)
    {
        return FacadeContext.getFurnitureCodeFacade().isUnique(value, FurnitureCode.PROPERTY_name,
                FurnitureCode.PROPERTY_code,
                FurnitureCode.PROPERTY_manufacturer,
                FurnitureCode.PROPERTY_groupIdentifier,
                AGTColor.PROPERTY_offsetLength,
                AGTColor.PROPERTY_offsetWidth);
    }
}
