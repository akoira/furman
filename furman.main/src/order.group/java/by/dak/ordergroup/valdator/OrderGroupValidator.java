package by.dak.ordergroup.valdator;

import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;


/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 31.03.2010
 * Time: 11:23:41
 * To change this template use File | Settings | File Templates.
 */
public class OrderGroupValidator extends AResourceValidator<OrderGroup>
{
    @Override
    public ValidationResult validate(OrderGroup orderGroup)
    {
        ValidationResult result = new ValidationResult();

        if (ValidationUtils.isEmpty(orderGroup.getName()))
        {
            result.addError(resourceMap.getString("validator.name"));
        }

        return result;
    }
}
