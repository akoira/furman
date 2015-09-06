package by.dak.persistence.entities.validator;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.store.tabs.TextureTab;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.TextureEntity;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;


/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 08.06.2009
 * Time: 21:57:41
 * To change this template use File | Settings | File Templates.
 */
public class TextureValidator extends AResourceValidator<TextureEntity>
{
    private ResourceMap resourceMap = Application.getInstance(
            CuttingApp.class).getContext().getResourceMap(TextureTab.class);

    @Override
    public ValidationResult validate(TextureEntity value)
    {
        ValidationResult validationResult = new ValidationResult();
        if (ValidationUtils.isEmpty(value.getName()))
        {
            validationResult.addError(resourceMap.getString("validator.namerus"));
        }

        if (ValidationUtils.isEmpty(value.getCode()))
        {
            validationResult.addError(resourceMap.getString("validator.fcode"));
        }

        if (ValidationUtils.isEmpty(value.getGroupIdentifier()))
        {
            validationResult.addError(resourceMap.getString("validator.groupidentifier"));
        }

        if (value.getManufacturer() == null)
        {
            validationResult.addError(resourceMap.getString("validator.manufacturer"));
        }

        if (!FacadeContext.getTextureFacade().isUniqueByCodeSurfaceManf(value))
        {
            validationResult.addError(resourceMap.getString("validator.uniqueCodeGroup"));
        }

        return validationResult;

    }
}
