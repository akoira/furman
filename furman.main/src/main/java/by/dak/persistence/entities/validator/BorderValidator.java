package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.Border;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 20.03.2010
 * Time: 17:05:04
 * To change this template use File | Settings | File Templates.
 */
public class BorderValidator extends AResourceValidator<Border>
{

    private PriceValidateHelper priceValidateHelper = new PriceValidateHelper(resourceMap);

    @Override
    public ValidationResult validate(Border border)
    {
        ValidationResult result = new ValidationResult();

        if (border.getBorderDef() == null)
        {
            result.addError(resourceMap.getString("validator.borderDef"));
        }

        if (border.getTexture() == null)
        {
            result.addError(resourceMap.getString("validator.texture"));
        }

        if (border.getLength() == null)
        {
            result.addError(resourceMap.getString("validator.length"));
        }

        if (border.getAmount() == null)
        {
            result.addError(resourceMap.getString("validator.amount"));
        }
        else if (by.dak.utils.validator.ValidationUtils.isLessThan(1, border.getAmount().longValue()))
        {
            result.addError(resourceMap.getString("validator.amount.range"));
        }


        return priceValidateHelper.validatePrice(border.getPriceAware(), border.getPriced(), border.getPrice(), result);
    }
}
