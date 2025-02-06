package by.dak.persistence.entities.validator;

import by.dak.persistence.entities.AServiceDetail;
import com.jgoodies.validation.ValidationResult;


public class AServiceLinkValidator<E extends AServiceDetail> extends AResourceValidator<E> {
    @Override
    public ValidationResult validate(E serviceLink) {
        ValidationResult result = new ValidationResult();

        if (serviceLink.getService() == null) {
            result.addError(resourceMap.getString("validator.type"));
        }

        if (serviceLink.getPriceAware() == null) {
            result.addError(resourceMap.getString("validator.code"));
        }

        return result;
    }
}
