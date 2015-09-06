package by.dak.persistence.entities.validator;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Cutter;
import com.jgoodies.validation.ValidationResult;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 03.03.11
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class CutterValidator extends AResourceValidator<Cutter>
{
    @Override
    public ValidationResult validate(Cutter cutter)
    {
        ValidationResult validationResult = new ValidationResult();
        if (StringUtils.isEmpty(cutter.getName()))
        {
            validationResult.addError(getResourceMap().getString("validator.name.empty"));
        }
        if (!cutter.hasId() && FacadeContext.getCutterFacade().findUniqueByField("name", cutter.getName()) != null)
        {
            validationResult.addError(getResourceMap().getString("validator.name.exist"));
        }
        return validationResult;
    }
}
