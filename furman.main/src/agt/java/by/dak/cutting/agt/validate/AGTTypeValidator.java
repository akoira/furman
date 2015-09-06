/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package by.dak.cutting.agt.validate;

import by.dak.cutting.agt.AGTType;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.persistence.entities.validator.FurnitureTypeValidator;
import com.jgoodies.validation.ValidationResult;

/**
 * @author Administrator
 */
public class AGTTypeValidator extends AResourceValidator<AGTType>
{
    private FurnitureTypeValidator furnitureTypeValidator = new FurnitureTypeValidator();

    @Override
    public ValidationResult validate(AGTType t)
    {
        ValidationResult result = furnitureTypeValidator.validate(t);
        return result;
    }

}
