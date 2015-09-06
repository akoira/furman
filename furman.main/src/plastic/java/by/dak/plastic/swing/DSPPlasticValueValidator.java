package by.dak.plastic.swing;

import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;

import java.util.List;

/**
 * User: akoyro
 * Date: 29.11.11
 * Time: 11:43
 */
public class DSPPlasticValueValidator extends AResourceValidator<DSPPlasticValue>
{
    @Override
    public ValidationResult validate(DSPPlasticValue plasticValue)
    {
        ValidationResult result = new ValidationResult();
        if (plasticValue.getDspPair().getTexture() == null)
        {
            result.addError(getResourceMap().getString("validator.dspPair.texture"));
        }

        if (plasticValue.getDspPair().getBoardDef() == null)
        {
            result.addError(getResourceMap().getString("validator.dspPair.boardDef"));
        }

        List<SidePair> pairs = plasticValue.getSidePairs();
        for (SidePair sidePair : pairs)
        {
            result.addAllFrom(ValidatorAnnotationProcessor.getProcessor().validate(sidePair));
        }
        return result;

    }
}
