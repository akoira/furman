package by.dak.utils.convert;

import java.lang.annotation.Annotation;
import java.util.HashMap;

/**
 * @author admin
 * @version 0.1 06.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class StringValueAnnotationProcessor
{
    private final static StringValueAnnotationProcessor PROCESSOR = new StringValueAnnotationProcessor();

    private HashMap<Class, EntityToStringConverter> converterMap = new HashMap<Class, EntityToStringConverter>();

    public static StringValueAnnotationProcessor getProcessor()
    {
        return PROCESSOR;
    }


    public static class DefaultConverter implements EntityToStringConverter
    {
        public String convert(Object entity)
        {

            return entity != null ? entity.toString() : Converter.EMPTY_STRING;
        }
    }

    public <T> String convert(T entity)
    {
        if (entity != null)
        {
            return getEntityToStringConverter(entity.getClass()).convert(entity);
        }
        else
        {
            return Converter.NULL_STRING;
        }
    }

    public EntityToStringConverter getEntityToStringConverter(Class annotatedClass)
    {
        EntityToStringConverter entityToStringConverter = converterMap.get(annotatedClass);
        if (entityToStringConverter != null)
        {
            return entityToStringConverter;
        }
        else
        {
            entityToStringConverter = createConverter(annotatedClass);
            converterMap.put(annotatedClass, entityToStringConverter);
            return entityToStringConverter;
        }
    }

    private EntityToStringConverter createConverter(Class annotatedClass)
    {
        Annotation annotation = annotatedClass.getAnnotation(StringValue.class);

        if (annotation == null)
        {
            return new DefaultConverter();
        }
        else
        {
            try
            {
                Class converter = ((StringValue) annotation).converterClass();
                EntityToStringConverter entityToStringConverter = (EntityToStringConverter) converter.newInstance();
                return entityToStringConverter;
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
