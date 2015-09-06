package by.dak.persistence.entities.validator;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.ProviderManufacturerBase;
import by.dak.utils.GenericUtils;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 05.02.2010
 * Time: 16:47:48
 * To change this template use File | Settings | File Templates.
 */
public class ProviderManufacturerValidator<E extends ProviderManufacturerBase> extends AResourceValidator<E>
{
    @Override
    public ValidationResult validate(E entity)
    {
        ValidationResult validationResult = new ValidationResult();

        if (ValidationUtils.isEmpty(entity.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
        }
        if (ValidationUtils.isEmpty(entity.getShortName()))
        {
            validationResult.addError(resourceMap.getString("validator.shortName"));
        }

        if (!FacadeContext.getFacadeBy(GenericUtils.getParameterClass(this.getClass(), 0)).isUnique(entity, "name"))
        {
            validationResult.addError(resourceMap.getString("validator.uniqueName", entity.getName()));
        }

        if (!FacadeContext.getFacadeBy(GenericUtils.getParameterClass(this.getClass(), 0)).isUnique(entity, "shortName"))
        {
            validationResult.addError(resourceMap.getString("validator.uniqueShortName", entity.getShortName()));
        }

        return validationResult;
    }
}
