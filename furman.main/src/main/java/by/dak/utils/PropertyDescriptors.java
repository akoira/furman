package by.dak.utils;

import org.jdesktop.el.ELException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * User: akoyro
 * Date: 19.06.2010
 * Time: 16:53:14
 */
public class PropertyDescriptors
{
    private final Class baseClass;
    private final Map<String, PropertyDescriptor> propertyMap =
            new HashMap<String, PropertyDescriptor>();

    public List<String> getEditableProperties()
    {
        return Collections.unmodifiableList(editableProperties);
    }

    private final List<String> editableProperties = new ArrayList<String>();

    public PropertyDescriptors(Class<?> baseClass)
    {
        this.baseClass = baseClass;
        PropertyDescriptor[] descriptors;
        try
        {
            BeanInfo info = Introspector.getBeanInfo(baseClass);
            descriptors = info.getPropertyDescriptors();
        }
        catch (IntrospectionException ie)
        {
            throw new ELException(ie);
        }
        for (PropertyDescriptor pd : descriptors)
        {
            propertyMap.put(pd.getName(),
                    pd);
            if (pd.getReadMethod() != null && pd.getWriteMethod() != null)
            {
                editableProperties.add(pd.getName());
            }
        }
    }

    public PropertyDescriptor getPropertyDescriptor(String property)
    {
        return propertyMap.get(property);
    }

}
