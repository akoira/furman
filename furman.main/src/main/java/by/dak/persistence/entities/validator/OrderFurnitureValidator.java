package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 12.03.2010
 * Time: 15:50:29
 * To change this template use File | Settings | File Templates.
 */
public class OrderFurnitureValidator extends AResourceValidator<AOrderBoardDetail>
{
    @Override
    public ValidationResult validate(AOrderBoardDetail orderFurniture)
    {
        ValidationResult result = new ValidationResult();

        if (orderFurniture.getBoardDef() == null)
        {
            result.addError(resourceMap.getString("validator.boardDef"));
        }

        if (orderFurniture.getTexture() == null)
        {
            result.addError(resourceMap.getString("validator.texture"));
        }

        if (ValidationUtils.isLessThan(20, orderFurniture.getLength()))
        {
            result.addError(resourceMap.getString("validator.length"));
        }

        if (ValidationUtils.isLessThan(20, orderFurniture.getWidth()))
        {
            result.addError(resourceMap.getString("validator.width"));
        }

        if (orderFurniture.isPrimary() == null)
        {
            result.addError(resourceMap.getString("validator.primary"));
        }

        if (ValidationUtils.isEmpty(orderFurniture.getName()))
        {
            result.addError(resourceMap.getString("validator.primary"));
        }

        if (ValidationUtils.isLessThan(1, orderFurniture.getNumber()))
        {
            result.addError(resourceMap.getString("validator.primary"));
        }

        if (ValidationUtils.isLessThan(1, orderFurniture.getAmount()))
        {
            result.addError(resourceMap.getString("validator.primary"));
        }

        return result;
    }
}