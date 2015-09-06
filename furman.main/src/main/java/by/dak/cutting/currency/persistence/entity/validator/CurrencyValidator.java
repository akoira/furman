package by.dak.cutting.currency.persistence.entity.validator;

import by.dak.cutting.currency.persistence.entity.Currency;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;

/**
 * User: akoyro
 * Date: 30.04.2010
 * Time: 12:30:49
 */
public class CurrencyValidator extends AResourceValidator<Currency>
{
    @Override
    public ValidationResult validate(Currency currency)
    {
        ValidationResult validationResult = new ValidationResult();

        if (ValidationUtils.isLessThan(0.01, currency.getPrice()))
        {
            validationResult.addError(resourceMap.getString("validator.price"));
        }
        else if (currency.getType() == null)
        {
            validationResult.addError(resourceMap.getString("validator.type"));
        }
        else if (currency.getDailysheet() == null)
        {
            validationResult.addError(resourceMap.getString("validator.dailysheet"));
        }
        else if (!FacadeContext.getCurrencyFacade().isUnique(currency, Currency.PROPERTY_type, Currency.PROPERTY_dailysheet))
        {
            validationResult.addError(resourceMap.getString("validator.unique.type.dailysheet"));
        }
        return validationResult;
    }
}
