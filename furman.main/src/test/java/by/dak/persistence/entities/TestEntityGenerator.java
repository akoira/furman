package by.dak.persistence.entities;

import by.dak.utils.convert.TimeUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author admin
 * @version 0.1 25.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class TestEntityGenerator<E extends PersistenceEntity>
{
    private Class<E> aClass;

    public static <E extends PersistenceEntity> TestEntityGenerator<E> getTestEntityGenerator(Class<E> c)
    {
        return new TestEntityGenerator<E>(c);
    }

    public TestEntityGenerator(Class<E> aClass)
    {
        super();
        this.aClass = aClass;
    }

    public E generate() throws Exception
    {
        E bean = aClass.newInstance();
        // Use JavaBeans to introspect the component
        // And get the list of properties it supports
        BeanInfo componentBeanInfo = Introspector
                .getBeanInfo(aClass);
        PropertyDescriptor[] properties = componentBeanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : properties)
        {
            Object value = getValue(aClass, property);
            if (value != null)
            {
                BeanUtils.setProperty(bean, property.getName(), value);
            }
        }
        return bean;
    }

    public Object getValue(Class beanClass, PropertyDescriptor propertyDescriptor)
    {
        if (propertyDescriptor.getName().equals("identity"))
        {
            return null;
        }
        else if (propertyDescriptor.getPropertyType().equals(String.class))
        {
            return StringUtils.join(new String[]{beanClass.getSimpleName(),
                    propertyDescriptor.getName(),
                    RandomStringUtils.random(10)}, '.');
        }
        else if (propertyDescriptor.getPropertyType().equals(Long.class))
        {
            if (propertyDescriptor.getName().equals("amount"))
            {
                return randomInt1_10();
            }
            return randomLong();
        }
        else if (propertyDescriptor.getPropertyType().equals(Boolean.class))
        {
            return RandomUtils.nextBoolean();
        }
        else if (propertyDescriptor.getPropertyType().equals(Integer.class))
        {
            return RandomUtils.nextInt();
        }
        else if (propertyDescriptor.getPropertyType().equals(Double.class))
        {
            return RandomUtils.nextDouble();
        }
        else if (propertyDescriptor.getPropertyType().equals(Timestamp.class))
        {
            return TimeUtils.getDayTimestamp(new Date(randomDate()));
        }
        else
        {
            return null;
        }
    }

    private int randomLong()
    {
        Random r = new Random();
        int randomTS = (int) (r.nextDouble() * 2000 - 100) + 100;
        return randomTS;
    }

    private int randomInt1_10()
    {
        Random r = new Random();
        int randomTS = (int) (r.nextDouble() * 10 - 1) + 10;
        return randomTS;
    }


    private long randomDate()
    {
        Calendar cdr = Calendar.getInstance();
        cdr.set(Calendar.HOUR_OF_DAY, 0);
        cdr.set(Calendar.MINUTE, 0);
        cdr.set(Calendar.SECOND, 0);
        cdr.set(Calendar.YEAR, 1900);
        cdr.set(Calendar.MONTH, 1);
        cdr.set(Calendar.DAY_OF_MONTH, 1);
        long val1 = cdr.getTimeInMillis();


        cdr.set(Calendar.YEAR, 3000);
        long val2 = cdr.getTimeInMillis();


        Random r = new Random();
        long randomTS = (long) (r.nextDouble() * (val2 - val1)) + val1;
        return randomTS;
    }
}


