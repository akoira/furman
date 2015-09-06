package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.Delivery;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 02.02.2010
 * Time: 9:51:06
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryValidator extends AResourceValidator<Delivery>
{
    @Override
    public ValidationResult validate(Delivery delivery)
    {
        ValidationResult result = new ValidationResult();

        if (delivery.getProvider() == null)
        {
            result.addError(resourceMap.getString("validator.provider"));
        }

        return result;
    }
}
