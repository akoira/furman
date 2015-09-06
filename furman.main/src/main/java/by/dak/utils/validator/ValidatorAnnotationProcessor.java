package by.dak.utils.validator;


import com.jgoodies.validation.ValidationResult;

import java.lang.annotation.Annotation;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 08.06.2009
 * Time: 22:03:58
 * To change this template use File | Settings | File Templates.
 */
public class ValidatorAnnotationProcessor
{
    private final static ValidatorAnnotationProcessor PROCESSOR = new ValidatorAnnotationProcessor();

    private HashMap<Class, com.jgoodies.validation.Validator> converterMap = new HashMap<Class, com.jgoodies.validation.Validator>();

    public static ValidatorAnnotationProcessor getProcessor()
    {
        return PROCESSOR;
    }


    public static class DefaultValidator implements com.jgoodies.validation.Validator
    {
        @Override
        public ValidationResult validate(Object value)
        {
            return new ValidationResult();
        }
    }

    public <T> ValidationResult validate(T entity)
    {
        if (entity != null)
            return getValidator(entity.getClass()).validate(entity);
        else
            return new ValidationResult();
    }

    public com.jgoodies.validation.Validator getValidator(Class annotatedClass)
    {
        com.jgoodies.validation.Validator validator = converterMap.get(annotatedClass);
        if (validator != null)
        {
            return validator;
        }
        else
        {
            validator = createValidator(annotatedClass);
            converterMap.put(annotatedClass, validator);
            return validator;
        }
    }

    private com.jgoodies.validation.Validator createValidator(Class annotatedClass)
    {
        Annotation annotation = annotatedClass.getAnnotation(by.dak.utils.validator.Validator.class);

        if (annotation == null)
        {
            return new DefaultValidator();
        }
        else
        {
            try
            {
                Class converter = ((Validator) annotation).validatorClass();
                com.jgoodies.validation.Validator validator = (com.jgoodies.validation.Validator) converter.newInstance();
                return validator;
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
