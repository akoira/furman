package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.Employee;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 05.02.2010
 * Time: 15:38:15
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeValidator extends AResourceValidator<Employee>
{
    @Override
    public ValidationResult validate(Employee employee)
    {
        ValidationResult validationResult = new ValidationResult();
        if (ValidationUtils.isEmpty(employee.getUserName()))
        {
            validationResult.addError(resourceMap.getString("validator.userName"));
        }
        if (ValidationUtils.isEmpty(employee.getPassword()))
        {
            validationResult.addError(resourceMap.getString("validator.password"));
        }
        // validate the name field
        if (ValidationUtils.isEmpty(employee.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
        }
        if (ValidationUtils.isEmpty(employee.getSurname()))
        {
            validationResult.addError(resourceMap.getString("validator.surname"));
        }
        if (employee.getDepartment() == null)
        {
            validationResult.addError(resourceMap.getString("validator.department"));
        }
        return validationResult;

    }
}
