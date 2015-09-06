package by.dak.persistence.entities.validator;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.01.2010
 * Time: 14:59:45
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTypeValidator extends AResourceValidator<FurnitureType>
{
    @Override
    public ValidationResult validate(FurnitureType value)
    {
        ValidationResult validationResult = new ValidationResult();
        if (ValidationUtils.isEmpty(value.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
        }

        if (value.getUnit() == null)
        {
            validationResult.addError(resourceMap.getString("validator.unit"));
        }

        if (!FacadeContext.getFurnitureTypeFacade().isUnique(value, "name"))
        {
            validationResult.addError(resourceMap.getString("validator.uniqueName"));
        }
        return validationResult;
    }
}
