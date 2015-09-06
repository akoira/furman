package by.dak.template.validator;

import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.template.TemplateOrder;
import com.jgoodies.validation.ValidationResult;

import static by.dak.utils.validator.ValidationUtils.isEmpty;

/**
 * User: akoyro
 * Date: 16.03.11
 * Time: 15:41
 */
public class TemplateOrderValidator extends AResourceValidator<TemplateOrder>
{
    @Override
    public ValidationResult validate(TemplateOrder orderOrder)
    {
        ValidationResult validationResult = new ValidationResult();
        if (isEmpty(orderOrder.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
        }
        if (isEmpty(orderOrder.getFileUuid()))
        {
            validationResult.addError(resourceMap.getString("validator.fileUuid"));
        }
        if (isEmpty(orderOrder.getDescription()))
        {
            validationResult.addError(resourceMap.getString("validator.description"));
        }
        return validationResult;
    }
}
