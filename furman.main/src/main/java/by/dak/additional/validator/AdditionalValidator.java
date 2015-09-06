package by.dak.additional.validator;

import by.dak.additional.Additional;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.application.Application;

/**
 * User: akoyro
 * Date: 19.06.2010
 * Time: 16:24:23
 */
public class AdditionalValidator extends AResourceValidator<Additional>
{

    public AdditionalValidator()
    {
        resourceMap = Application.getInstance().getContext().getResourceMap(Additional.class);
    }

    @Override
    public ValidationResult validate(Additional additional)
    {
        ValidationResult validationResult = new ValidationResult();

        if (ValidationUtils.isEmpty(additional.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
        }

        if (ValidationUtils.isEmpty(additional.getType()))
        {
            validationResult.addError(resourceMap.getString("validator.type"));
        }

        if (ValidationUtils.isLessThan(0.00001, additional.getSize()))
        {
            validationResult.addError(resourceMap.getString("validator.size"));
        }

        if (ValidationUtils.isLessThan(0.00001, additional.getPrice()))
        {
            validationResult.addError(resourceMap.getString("validator.price"));
        }
        return validationResult;
    }
}
