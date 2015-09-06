package by.dak.cutting.facade.impl.helper;

import by.dak.persistence.entities.AStoreElement;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.persistence.entities.validator.AResourceValidator;
import by.dak.utils.validator.Validator;
import com.jgoodies.validation.ValidationResult;

/**
 * Заменяет тип и код  в заказной детали
 * User: akoyro
 * Date: 07.11.2010
 * Time: 11:15:47
 */

@Validator(validatorClass = FurnitureTypeCodeReplacer.FurnitureTypeCodeReplacerValidator.class)

public class FurnitureTypeCodeReplacer
{
    private AStoreElement storeElement;
    private PriceAware type;
    private Priced code;


    public void replace()
    {
        //storeElement.
    }


    public AStoreElement getStoreElement()
    {
        return storeElement;
    }

    public void setStoreElement(AStoreElement storeElement)
    {
        this.storeElement = storeElement;
    }

    public PriceAware getType()
    {
        return type;
    }

    public void setType(PriceAware type)
    {
        this.type = type;
    }

    public Priced getCode()
    {
        return code;
    }

    public void setCode(Priced code)
    {
        this.code = code;
    }

    public static class FurnitureTypeCodeReplacerValidator extends AResourceValidator<FurnitureTypeCodeReplacer>
    {
        @Override
        public ValidationResult validate(FurnitureTypeCodeReplacer furnitureTypeCodeReplacer)
        {
            ValidationResult validationResult = new ValidationResult();
            if (furnitureTypeCodeReplacer.getStoreElement() == null)
            {
                validationResult.addError(getResourceMap().getString("validator.storeElement"));
            }

            if (furnitureTypeCodeReplacer.getStoreElement() != null &&
                    furnitureTypeCodeReplacer.getStoreElement().getStatus() != StoreElementStatus.order)
            {
                validationResult.addError(getResourceMap().getString("validator.storeElement.status"));
            }

            return validationResult;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
