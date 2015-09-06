package by.dak.persistence.entities.validator;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.dictionaries.TextureConfigurePanel;
import by.dak.persistence.entities.PriceEntity;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * User: akoyro
 * Date: 08.06.2009
 * Time: 22:12:42
 */
public class PriceValidator extends AResourceValidator<PriceEntity>
{

    private ResourceMap resourceMap = Application.getInstance(
            CuttingApp.class).getContext().getResourceMap(TextureConfigurePanel.class);

    @Override
    public ValidationResult validate(PriceEntity value)
    {
        ValidationResult validationResult = new ValidationResult();
        if (value.getPriceAware() != null)
        {
            validationResult.addAllFrom(ValidatorAnnotationProcessor.getProcessor().validate(value.getPriceAware()));
        }
        else
        {
            validationResult.addError(resourceMap.getString("validator.priceAware"));
        }
        if (value.getPriced() != null)
        {
            if (!value.getPriced().hasId())
            {
                validationResult.addAllFrom(ValidatorAnnotationProcessor.getProcessor().validate(value.getPriced()));
            }
        }
        else
        {
            validationResult.addError(resourceMap.getString("validator.priced"));
        }
        return validationResult;
    }
}
