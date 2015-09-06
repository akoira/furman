/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package by.dak.cutting.afacade.validate;

import by.dak.cutting.afacade.AProfileType;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.persistence.entities.validator.FurnitureTypeValidator;
import com.jgoodies.validation.ValidationResult;

/**
 * @author Administrator
 */
public abstract class AProfileTypeValidator<E extends AProfileType> extends AResourceValidator<E>
{
    private FurnitureTypeValidator furnitureTypeValidator = new FurnitureTypeValidator();

    @Override
    public ValidationResult validate(E t)
    {
        ValidationResult result = furnitureTypeValidator.validate(t);
        return result;
    }

}
