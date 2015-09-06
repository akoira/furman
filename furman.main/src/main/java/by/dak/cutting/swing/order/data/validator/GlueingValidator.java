package by.dak.cutting.swing.order.data.validator;

import by.dak.cutting.swing.order.data.Glueing;
import by.dak.cutting.swing.order.data.GlueingSideHelper;
import by.dak.persistence.entities.predefined.Side;
import by.dak.persistence.entities.validator.AResourceValidator;
import com.jgoodies.validation.ValidationResult;

/**
 * User: akoyro
 * Date: 12.11.2010
 * Time: 14:05:50
 */
public class GlueingValidator extends AResourceValidator<Glueing>
{
    @Override
    public ValidationResult validate(Glueing glueing)
    {
        ValidationResult result = new ValidationResult();
        Side[] sides = Side.values();
        for (Side side : sides)
        {
            GlueingSideHelper helper = new GlueingSideHelper(glueing, side);
            if (helper.isGlueing() && (helper.getBorderDef() == null || helper.getTexture() == null))
            {
                result.addError(getResourceMap().getString("validator.glueing"));
            }
        }
        return result;
    }
}
