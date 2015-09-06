package by.dak.persistence.entities.validator;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Priced;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.application.ResourceMap;

/**
 * Проверяет правельность установки прайса для материала
 * User: akoyro
 * Date: 17.03.2010
 * Time: 13:57:43
 */
public class PriceValidateHelper
{
    private ResourceMap resourceMap;

    public PriceValidateHelper(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }

    public ValidationResult validatePrice(PriceAware priceAware, Priced priced,
                                          Double currentPrice, ValidationResult validationResult)
    {
        PriceEntity price = FacadeContext.getPriceFacade().findUniqueBy(priceAware, priced);
        //проверяем есть ли прайс
        if (price == null)
        {
            validationResult.addWarning(resourceMap.getString("validator.withoutPrice",
                    StringValueAnnotationProcessor.getProcessor().convert(priceAware),
                    StringValueAnnotationProcessor.getProcessor().convert(priced)));
        }

        if (price != null && currentPrice > price.getPrice())
        {
            validationResult.addWarning(resourceMap.getString("validator.moreThanPrice",
                    price.getPrice()));
        }
        return validationResult;
    }
}
