package by.dak.cutting.agt.validate;

import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.afacade.validate.AFacadeValidator;
import by.dak.cutting.agt.AGTFacade;
import by.dak.cutting.agt.facade.AGTFacadeFacadeImpl;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;

/**
 * User: akoyro
 * Date: 01.09.2010
 * Time: 18:05:34
 */
public class AGTFacadeValidator extends AResourceValidator<AGTFacade>
{
    public static final Double MIN_FACADE_FILLING_SIZE = 10D;
    private AFacadeValidator facadeValidator = new AFacadeValidator()
    {
        @Override
        protected boolean validateLengthAndWidth(AFacade facade, ValidationResult validationResult)
        {
            double profileWidth = AGTFacadeFacadeImpl.getProfileWidth((AGTFacade) facade);
            if (facade.getLength() - 2 * profileWidth < MIN_FACADE_FILLING_SIZE)
            {
                validationResult.addError(resourceMap.getString("validator.length"));
                return false;
            }
            if (facade.getWidth() - 2 * profileWidth < MIN_FACADE_FILLING_SIZE)
            {
                validationResult.addError(resourceMap.getString("validator.width"));
                return false;
            }

            return true;
        }
    };

    @Override
    public ValidationResult validate(AGTFacade facade)
    {
        ValidationResult validationResult = facadeValidator.validate(facade);
        validationResult.addAllFrom(ValidatorAnnotationProcessor.getProcessor().validate(facade.getDowel()));
        return validationResult;
    }


}
