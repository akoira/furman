package by.dak.template.validator;

import by.dak.cutting.afacade.validate.AFacadeValidator;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.template.TemplateFacade;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.application.Application;

/**
 * User: akoyro
 * Date: 01.09.2010
 * Time: 18:05:34
 */
public class TemplateFacadeValidator extends AResourceValidator<TemplateFacade>
{
    public TemplateFacadeValidator()
    {
        setResourceMap(Application.getInstance().getContext().getResourceMap(AFacadeValidator.class));
    }

    @Override
    public ValidationResult validate(TemplateFacade facade)
    {
        ValidationResult validationResult = new ValidationResult();

        if (ValidationUtils.isEmpty(facade.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
            return validationResult;
        }

        if (ValidationUtils.isLessThan(1, facade.getNumber()))
        {
            validationResult.addError(resourceMap.getString("validator.number"));
            return validationResult;
        }

        if (ValidationUtils.isLessThan(1, facade.getAmount()))
        {
            validationResult.addError(resourceMap.getString("validator.amount"));
            return validationResult;
        }

        if (ValidationUtils.isLessThan(AFacadeValidator.MIN_FACADE_SIZE, facade.getLength()))
        {
            validationResult.addError(resourceMap.getString("validator.length"));
            return validationResult;
        }

        if (ValidationUtils.isLessThan(AFacadeValidator.MIN_FACADE_SIZE, facade.getWidth()))
        {
            validationResult.addError(resourceMap.getString("validator.width"));
            return validationResult;
        }

        return validationResult;
    }
}
