package by.dak.utils.validator;

import com.jgoodies.validation.ValidationResult;
import org.apache.commons.lang3.StringUtils;

public class ValidationUtils
{
    public static boolean isMoreThan(long moreThan, Long value)
    {
        return value != null && value > moreThan;
    }

    public static boolean isMoreThan(double moreThan, Double value)
    {
        return value != null && value > moreThan;
    }

    public static boolean isLessThan(long lessThan, Integer value)
    {
        return value == null || value < lessThan;
    }

    public static boolean isLessThan(long lessThan, Long value)
    {
        return value == null || value < lessThan;
    }

    public static boolean isLessThan(long lessThan, Double value)
    {
        return value == null || value < lessThan;
    }


    public static boolean isLessThan(Double lessThan, Double value)
    {
        return value == null || value < lessThan;
    }

    public static boolean isErrors(ValidationResult validationResult)
    {
        return validationResult.getErrors() != null && validationResult.getErrors().size() > 0;
    }

    public static boolean isEmpty(String value)
    {
        return StringUtils.isEmpty(StringUtils.trimToEmpty(value));
    }
}