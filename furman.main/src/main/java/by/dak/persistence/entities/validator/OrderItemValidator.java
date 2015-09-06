package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.OrderItem;
import com.jgoodies.validation.ValidationResult;

import static by.dak.utils.validator.ValidationUtils.isEmpty;


/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 14:15:26
 */
public class OrderItemValidator extends AResourceValidator<OrderItem>
{
    @Override
    public ValidationResult validate(OrderItem orderItem)
    {
        ValidationResult validationResult = new ValidationResult();
        if (isEmpty(orderItem.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
        }
        return validationResult;
    }
}
