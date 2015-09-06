package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.AFurnitureDetail;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 12.03.2010
 * Time: 15:50:29
 * To change this template use File | Settings | File Templates.
 */
public class AFurnitureLinkValidator<E extends AFurnitureDetail> extends AResourceValidator<E>
{
    @Override
    public ValidationResult validate(E furnitureLink)
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

        return result;
    }
}
