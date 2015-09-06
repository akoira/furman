package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 12.03.2010
 * Time: 15:50:29
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureLinkValidator extends AFurnitureLinkValidator<FurnitureLink>
{
    @Override
    public ValidationResult validate(FurnitureLink furnitureLink)
    {
        ValidationResult result = new ValidationResult();

        if (furnitureLink.getFurnitureCode() == null)
        {
            result.addError(resourceMap.getString("validator.code"));
        }

        if (furnitureLink.getFurnitureType() == null)
        {
            result.addError(resourceMap.getString("validator.type"));
        }

        if (ValidationUtils.isLessThan(0.001, furnitureLink.getSize()))
        {
            result.addError(resourceMap.getString("validator.size"));
        }

        if (furnitureLink.getAmount() == null)
        {
            result.addError(resourceMap.getString("validator.amount"));
        }

        if (furnitureLink.getFurnitureType() != null)
        {
            if (furnitureLink.getFurnitureType().getUnit().equals(Unit.linearMetre))
            {
                if (ValidationUtils.isMoreThan(furnitureLink.getFurnitureType().getDefaultSize(),
                        furnitureLink.getSize()))
                {
                    result.addError(resourceMap.getString("validator.defalutSize"));
                }
            }
        }
        return result;
    }
}
