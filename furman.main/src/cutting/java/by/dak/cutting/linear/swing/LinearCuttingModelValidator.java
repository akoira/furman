package by.dak.cutting.linear.swing;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.linear.FurnitureTypeCodePair;
import by.dak.cutting.linear.LinearCuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.validator.AResourceValidator;
import com.jgoodies.validation.ValidationResult;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 09.05.11
 * Time: 0:30
 * To change this template use File | Settings | File Templates.
 */
public class LinearCuttingModelValidator extends AResourceValidator<LinearCuttingModel>
{
    @Override
    public ValidationResult validate(LinearCuttingModel cuttingModel)
    {
        ValidationResult validationResult = new ValidationResult();
        List<FurnitureTypeCodePair> pairs = cuttingModel.getPairs();
        for (FurnitureTypeCodePair pair : pairs)
        {
            Strips strips = cuttingModel.getStrips(pair);
            if (strips == null)
            {
                validationResult.addError(getResourceMap().getString("validator.size"));
            }

            Element[] elements = strips.getCuttedElements();
            if (elements == null || elements.length < 1)
            {
                validationResult.addError(getResourceMap().getString("validator.size"));
                return validationResult;
            }

            int amount = FacadeContext.getFurnitureLinkFacade().getCountBy(cuttingModel.getOrderGroup(),
                    pair);


            if (elements.length != amount)
            {
                validationResult.addError(getResourceMap().getString("validator.size"));
                return validationResult;
            }

        }
        return validationResult;
    }
}
