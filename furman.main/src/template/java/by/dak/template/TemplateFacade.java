package by.dak.template;

import by.dak.cutting.afacade.AFacade;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.template.validator.TemplateFacadeValidator;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: akoyro
 * Date: 11/15/13
 * Time: 5:12 AM
 */

@Entity
@DiscriminatorValue(value = "TemplateFacade")
@DiscriminatorOptions(force = true)
@Validator(validatorClass = TemplateFacadeValidator.class)
public class TemplateFacade extends AFacade
{
    public TemplateFacade()
    {
        setType(OrderItemType.templateFacade);
    }
}
