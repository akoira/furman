package by.dak.persistence.entities.validator;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import com.jgoodies.validation.ValidationResult;

import static by.dak.utils.validator.ValidationUtils.isEmpty;
import static by.dak.utils.validator.ValidationUtils.isLessThan;


public class BoardDefValidator extends AResourceValidator<BoardDef>
{
    @Override
    public ValidationResult validate(BoardDef boardDef)
    {
        ValidationResult validationResult = new ValidationResult();
        if (boardDef == null)
        {
            validationResult.addError("");
            return validationResult;
        }
        // validate the name field
        if (isEmpty(boardDef.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.name"));
        }

        if (!FacadeContext.getBoardDefFacade().isUnique(boardDef, "name"))
        {
            validationResult.addError(resourceMap.getString("validator.uniqueName"));
        }

        if (isLessThan(1, boardDef.getThickness()))
        {
            validationResult.addError(resourceMap.getString("validator.thickness"));
        }

        if (isLessThan(1, boardDef.getDefaultLength()))
        {
            validationResult.addError(resourceMap.getString("validator.defaultLength"));
        }

        if (isLessThan(1, boardDef.getDefaultWidth()))
        {
            validationResult.addError(resourceMap.getString("validator.defaultWidth"));
        }

        if (boardDef.getReservedLength() == null)
        {
            validationResult.addError(resourceMap.getString("validator.reservedLength"));
        }

        if (boardDef.getReservedWidth() == null)
        {
            validationResult.addError(resourceMap.getString("validator.reservedWidth"));
        }


        if (boardDef.getCutter() == null)
        {
            validationResult.addError(resourceMap.getString("validator.cutter"));
        }
        return validationResult;
    }
}