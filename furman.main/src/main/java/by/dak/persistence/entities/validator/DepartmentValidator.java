package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.DepartmentEntity;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;

/**
 * User: akoyro
 * Date: 04.04.11
 * Time: 14:57
 */
public class DepartmentValidator extends AResourceValidator<DepartmentEntity>
{
    @Override
    public ValidationResult validate(DepartmentEntity departmentEntity)
    {
        ValidationResult validationResult = new ValidationResult();
        //validate the name field
        if (ValidationUtils.isEmpty(departmentEntity.getName()))
        {
            validationResult.addError(getResourceMap().getString("validator.name"));
        }

        return validationResult;
    }
}
