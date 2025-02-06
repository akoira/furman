package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.ServiceLink;
import by.dak.persistence.entities.predefined.Unit;
import by.dak.utils.validator.ValidationUtils;
import com.jgoodies.validation.ValidationResult;


public class ServiceLinkValidator extends AServiceLinkValidator<ServiceLink> {
    @Override
    public ValidationResult validate(ServiceLink serviceLink) {
        ValidationResult result = new ValidationResult();

        if (serviceLink.getService() == null) {
            result.addError(resourceMap.getString("validator.type"));
        }

        if (serviceLink.getPriceAware() == null) {
            result.addError(resourceMap.getString("validator.code"));
        }

        if (serviceLink.getAmount() == null) {
            result.addError(resourceMap.getString("validator.amount"));
        }

        return result;
    }
}
