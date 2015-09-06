package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.Furniture;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 29.01.2010
 * Time: 16:18:50
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureValidator extends AResourceValidator<Furniture>
{
    private PriceValidateHelper priceValidateHelper = new PriceValidateHelper(resourceMap);


    @Override
    public ValidationResult validate(Furniture furniture)
    {
        ValidationResult validationResult = new ValidationResult();

        if (furniture.getFurnitureCode() == null)
        {
            validationResult.addError(resourceMap.getString("validator.furnitureCode"));
        }

        if (furniture.getFurnitureType() == null)
        {
            validationResult.addError(resourceMap.getString("validator.furnitureType"));
        }

        if (ValidationUtils.isLessThan(0.01, furniture.getSize()))
        {
            validationResult.addError(resourceMap.getString("validator.size"));
        }

        return priceValidateHelper.validatePrice(furniture.getFurnitureType(), furniture.getFurnitureCode(), furniture.getPrice(), validationResult);
    }
}
