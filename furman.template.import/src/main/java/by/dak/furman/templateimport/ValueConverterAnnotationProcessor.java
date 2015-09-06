package by.dak.furman.templateimport;

import by.dak.furman.templateimport.swing.nodes.AValueConverter;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.lang.annotation.Annotation;
import java.util.HashMap;

public class ValueConverterAnnotationProcessor
{
    private ResourceMap resourceMap;

    private HashMap<Class, IValueConverter> converterMap = new HashMap<Class, IValueConverter>();

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }

    public static class DefaultConverter implements IValueConverter
    {
        @Override
        public Icon getIcon(Object value)
        {
            return null;
        }

        @Override
        public String getString(Object value)
        {
            return value.toString();
        }
    }

    public <V> IValueConverter<V> getValueConverter(Class<V> annotatedClass)
    {
        IValueConverter converter = converterMap.get(annotatedClass);
        if (converter != null)
            return converter;
        else
        {
            converter = createConverter(annotatedClass);
            converterMap.put(annotatedClass, converter);
        }
        return converter;
    }

    private IValueConverter createConverter(Class annotatedClass)
    {
        Annotation annotation = annotatedClass.getAnnotation(ValueConverter.class);

        if (annotation == null)
        {
            return new DefaultConverter();
        }
        else
        {
            try
            {
                Class aClass = ((ValueConverter) annotation).converter();
                IValueConverter converter = (IValueConverter) aClass.newInstance();
                if (converter instanceof AValueConverter)
                    ((AValueConverter) converter).setResourceMap(resourceMap);
                return converter;
            }
            catch (InstantiationException e)
            {
                throw new IllegalArgumentException(e);
            }
            catch (IllegalAccessException e)
            {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
