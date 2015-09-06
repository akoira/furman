package by.dak.persistence.entities.validator;

import by.dak.cutting.swing.store.tabs.ServiceTab;
import by.dak.persistence.entities.Service;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * User: akoyro
 * Date: 20.06.2009
 * Time: 15:14:12
 */
public class ServiceValidator extends AResourceValidator<Service>
{
    private ResourceMap rsMap = Application.getInstance().getContext().getResourceMap(ServiceTab.class);

    @Override
    public ValidationResult validate(Service value)
    {
        ValidationResult validationResult = new ValidationResult();

        if (value.getServiceType() == null)
        {
            validationResult.addError(rsMap.getString("validator.serviceType"));
        }
        return validationResult;
    }
}
