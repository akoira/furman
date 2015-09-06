package by.dak.persistence.entities.validator;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.01.2010
 * Time: 14:59:58
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureCodeValidator<E extends FurnitureCode> extends AResourceValidator<E>
{
    @Override
    public ValidationResult validate(E value)
    {
        ValidationResult validationResult = new ValidationResult();
        if (ValidationUtils.isEmpty(value.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
        }

        if (ValidationUtils.isEmpty(value.getCode()))
        {
            validationResult.addError(resourceMap.getString("validator.code"));
        }

        if (value.getManufacturer() == null)
        {
            validationResult.addError(resourceMap.getString("validator.manufacturer"));
        }

        if (!isUnique(value))
        {
            validationResult.addError(resourceMap.getString("validator.uniqueNameCodeManf"));
        }

        return validationResult;
    }

    protected boolean isUnique(E value)
    {
        return FacadeContext.getFurnitureCodeFacade().isUnique(value, FurnitureCode.PROPERTY_name, FurnitureCode.PROPERTY_code, FurnitureCode.PROPERTY_manufacturer, FurnitureCode.PROPERTY_groupIdentifier);
    }
}
