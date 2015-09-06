/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package by.dak.cutting.zfacade.validator;

import by.dak.cutting.zfacade.ZProfileType;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.persistence.entities.validator.FurnitureTypeValidator;
import com.jgoodies.validation.ValidationResult;

/**
 *
 * @author Administrator
 */
public class ZProfileTypeValidator extends AResourceValidator<ZProfileType>
{
    private FurnitureTypeValidator furnitureTypeValidator = new FurnitureTypeValidator();

    @Override
    public ValidationResult validate(ZProfileType t)
    {
        ValidationResult result = furnitureTypeValidator.validate(t);

        FacadeContext.getZProfileTypeFacade().fillChildTypes(t);

//        if (t.getRubberType() == null)
//        {
//            result.addError(resourceMap.getString("validator.rubberType"));
//        }

        if (t.getAngleType() == null)
        {
            result.addError(resourceMap.getString("validator.angleType"));
        }
        return result;
    }

}
