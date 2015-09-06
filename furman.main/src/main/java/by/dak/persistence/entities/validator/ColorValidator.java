package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.Color;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;


public class ColorValidator extends AResourceValidator<Color>
{
    @Override
    public ValidationResult validate(Color value)
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

//        if (!FacadeContext.getFacadeBy(Color.class).isUnique(value, ))
//        {
//            validationResult.addError(resourceMap.getString("validator.uniqueCodeGroup"));
//        }

        return null;
    }
}
