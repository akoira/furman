package by.dak.cutting.zfacade.validator;

import by.dak.cutting.afacade.validate.AFacadeValidator;
import by.dak.cutting.zfacade.ZFacade;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;

/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 20:05:24
 */
public class ZFacadeValidator extends AResourceValidator<ZFacade>
{
    private AFacadeValidator aFacadeValidator = new AFacadeValidator();

    @Override
    public ValidationResult validate(ZFacade facade)
    {
        ValidationResult validationResult = aFacadeValidator.validate(facade);
        validationResult.addAllFrom(ValidatorAnnotationProcessor.getProcessor().validate(facade.getAngle()));
        //validationResult.addAllFrom(ValidatorAnnotationProcessor.getProcessor().validate(facade.getRubber()));
        return validationResult;
    }
}
