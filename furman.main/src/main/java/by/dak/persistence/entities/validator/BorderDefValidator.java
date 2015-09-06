package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.BorderDefEntity;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;


public class BorderDefValidator extends AResourceValidator<BorderDefEntity>
{
    @Override
    public ValidationResult validate(BorderDefEntity borderDef)
    {
        ValidationResult validationResult = new ValidationResult();
        if (ValidationUtils.isEmpty(borderDef.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
        }/*else if (FacadeContext.getBorderDefFacade()
                .findUniqueByField("name", fieldName.getText())!=null){
            validationResult.addError(resourceMap.getString("validator.name.duplicate"));
        }*/


        if (ValidationUtils.isLessThan(1, borderDef.getHeight()))
        {
            validationResult.addError(resourceMap.getString("validator.height"));
        }/*else if (!ValidationUtils.isNumeric(fieldHeight.getText())){
            validationResult.addError(resourceMap.getString("validator.height.numeric"));
        }*/

        if (ValidationUtils.isLessThan(1, borderDef.getThickness()))
        {
            validationResult.addError(resourceMap.getString("validator.thickness"));
        }/*else if (!ValidationUtils.isNumeric(fieldThickness.getText())){
            validationResult.addError(resourceMap.getString("validator.thickness.numeric"));
        }*/

        return validationResult;
    }
}
