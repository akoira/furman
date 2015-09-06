package by.dak.cutting.zfacade.validator;

import by.dak.cutting.zfacade.ZButtLink;
import by.dak.persistence.entities.validator.AFurnitureLinkValidator;
import com.jgoodies.validation.ValidationResult;

/**
 * User: akoyro
 * Date: 31.07.2010
 * Time: 22:35:12
 */
public class ZButtLinkValidator extends AFurnitureLinkValidator<ZButtLink>
{
    @Override
    public ValidationResult validate(ZButtLink zButtLink)
    {
        ValidationResult result = super.validate(zButtLink);
        if (zButtLink.getSide() == null)
        {
            result.addError(resourceMap.getString("validator.side"));
        }
        return result;
    }
}
