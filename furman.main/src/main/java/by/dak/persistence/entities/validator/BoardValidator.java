package by.dak.persistence.entities.validator;

import by.dak.cutting.configuration.Constants;
import by.dak.persistence.entities.Board;
import com.jgoodies.validation.ValidationResult;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2010
 * Time: 17:22:33
 * To change this template use File | Settings | File Templates.
 */
public class BoardValidator extends AResourceValidator<Board>
{
    private PriceValidateHelper priceValidateHelper = new PriceValidateHelper(resourceMap);

    @Override
    public ValidationResult validate(Board board)
    {
        ValidationResult validationResult = new ValidationResult();
        // validate the name field
        if (board.getBoardDef() == null)
        {
            validationResult.addError(resourceMap.getString("validator.boardDef"));
        }

        if (board.getTexture() == null)
        {
            validationResult.addError(resourceMap.getString("validator.texture"));
        }

        if (board.getAmount() == null || by.dak.utils.validator.ValidationUtils.isLessThan(1, board.getAmount().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.amount"));
        }

        if (board.getLength() == null || by.dak.utils.validator.ValidationUtils.isLessThan(Constants.MIN_MATERIAL_LENGTH,
                board.getLength().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.length"));
        }

        if (board.getWidth() == null || by.dak.utils.validator.ValidationUtils.isLessThan(Constants.MIN_MATERIAL_WIDTH,
                board.getWidth().longValue()))
        {
            validationResult.addError(resourceMap.getString("validator.width"));
        }

        if (board.getPrice() == null || by.dak.utils.validator.ValidationUtils.isLessThan(1,
                board.getPrice()))
        {
            validationResult.addError(resourceMap.getString("validator.price"));
        }

        return priceValidateHelper.validatePrice(board.getPriceAware(), board.getPriced(), board.getPrice(), validationResult);
    }

}
