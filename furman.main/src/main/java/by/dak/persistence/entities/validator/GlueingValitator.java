package by.dak.persistence.entities.validator;

import by.dak.cutting.swing.order.data.Glueing;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 27.02.2010
 * Time: 23:18:48
 * To change this template use File | Settings | File Templates.
 */
public class GlueingValitator extends AResourceValidator<Glueing>
{
    @Override
    public ValidationResult validate(Glueing glueing)
    {
        ValidationResult result = new ValidationResult();
        if (glueing != null)
        {
            if (glueing.isLeft() && !(glueing.getLeftBorderDef() != null && glueing.getLeftTexture() != null))
            {
                result.addError(resourceMap.getString("left.empty"));
            }
            if (glueing.isRight() && !(glueing.getRightBorderDef() != null && glueing.getRightTexture() != null))
            {
                result.addError(resourceMap.getString("right.empty"));
            }
            if (glueing.isUp() && !(glueing.getUpBorderDef() != null && glueing.getUpTexture() != null))
            {
                result.addError(resourceMap.getString("up.empty"));
            }
            if (glueing.isDown() && !(glueing.getDownBorderDef() != null && glueing.getDownTexture() != null))
            {
                result.addError(resourceMap.getString("down.empty"));
            }
        }
        return result;
    }
}
