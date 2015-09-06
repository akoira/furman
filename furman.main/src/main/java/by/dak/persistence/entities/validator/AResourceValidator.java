package by.dak.persistence.entities.validator;

import com.jgoodies.validation.Validator;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public abstract class AResourceValidator<E> implements Validator<E>
{

    protected ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(this.getClass(), AResourceValidator.class);

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }
}
