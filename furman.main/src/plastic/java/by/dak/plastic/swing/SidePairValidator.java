package by.dak.plastic.swing;

import by.dak.persistence.entities.validator.AResourceValidator;
import com.jgoodies.validation.ValidationResult;

/**
 * User: akoyro
 * Date: 29.11.11
 * Time: 11:53
 */
public class SidePairValidator extends AResourceValidator<SidePair>
{
    @Override
    public ValidationResult validate(SidePair sidePair)
    {
        ValidationResult result = new ValidationResult();
        if (sidePair.getNeed() && sidePair.getSecond().getTexture() == null)
        {
            result.addError(getResourceMap().getString("validator.second.texture"));
        }
        return result;
    }
}
