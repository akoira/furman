package by.dak.utils.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 27.12.2008
 * Time: 19:54:21
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.TYPE)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface StringValue
{
    public Class<? extends EntityToStringConverter> converterClass();
}
