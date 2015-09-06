package by.dak.cutting.afacade.validate;

import by.dak.cutting.afacade.AFacade;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;

/**
 * User: akoyro
 * Date: 06.09.2010
 * Time: 22:17:45
 */
public class AFacadeValidator extends AResourceValidator<AFacade>
{
    public static final Double MIN_FACADE_SIZE = 166D;

    @Override
    public ValidationResult validate(AFacade facade)
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


        if (facade.getProfileType() == null)
        {
            validationResult.addError(resourceMap.getString("validator.type"));
            return validationResult;
        }

        if (facade.getProfileType() == null)
        {
            validationResult.addError(resourceMap.getString("validator.color"));
            return validationResult;
        }

        if (ValidationUtils.isLessThan(1, facade.getAmount()))
        {
            validationResult.addError(resourceMap.getString("validator.amount"));
            return validationResult;
        }

        if (!validateLengthAndWidth(facade, validationResult))
        {
            return validationResult;
        }

        return validationResult;
    }

    protected boolean validateLengthAndWidth(AFacade facade, ValidationResult validationResult)
    {
        if (ValidationUtils.isLessThan(MIN_FACADE_SIZE, facade.getLength()))
        {
            validationResult.addError(resourceMap.getString("validator.length"));
            return false;
        }

        if (ValidationUtils.isLessThan(MIN_FACADE_SIZE, facade.getWidth()))
        {
            validationResult.addError(resourceMap.getString("validator.width"));
            return false;
        }
        return true;
    }
}
